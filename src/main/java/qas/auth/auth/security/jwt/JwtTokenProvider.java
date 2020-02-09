package qas.auth.auth.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import qas.auth.auth.dto.RoleDto;
import qas.auth.auth.dto.UserDto;
import qas.auth.auth.dto.ValidatedToken;
import qas.auth.auth.services.iService.IUserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validityInMillies;

    private final IUserService userService;


    public JwtTokenProvider(IUserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }


    public String createToken(UserDto userDto) {
        Claims claims = Jwts.claims().setSubject(userDto.getLogin());
        claims.put("roles", getRoleNames(userDto.getRoles()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMillies);

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public ValidatedToken validateToken(String token) {
        ValidatedToken validatedToken = new ValidatedToken();
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            String username = getUserName(token);
            UserDto userDto = userService.findByUserName(username);
            if (!userDto.getToken().equals(token)) {
                throw new BadCredentialsException("Wrong token");
            }

            if (claims.getBody().getExpiration().before(new Date())) {
                throw new ExpiredJwtException(claims.getHeader(), claims.getBody(), "token expired");
            }
        } catch (ExpiredJwtException | UnsupportedJwtException
                | MalformedJwtException | SignatureException
                | IllegalArgumentException | BadCredentialsException e) {
            e.printStackTrace();
            validatedToken.setValidated(false);
            validatedToken.setMessage(e.toString());
            return validatedToken;
        }
        validatedToken.setValidated(true);
        return validatedToken;
    }

    private List<String> getRoleNames(List<RoleDto> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(roleDto -> {
            result.add(roleDto.getName());
        });
        return result;
    }

    private String getUserName(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
