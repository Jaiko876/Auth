package qas.auth.auth.controller;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import qas.auth.auth.dto.RegObj;
import qas.auth.auth.dto.UserDto;
import qas.auth.auth.dto.ValidatedToken;
import qas.auth.auth.security.jwt.JwtTokenProvider;
import qas.auth.auth.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        JwtTokenProvider jwtTokenProvider, @Lazy UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody RegObj regObj) {
        try {
            userService.checkLogin(regObj.getUsername());
            UserDto user = userService.findByUserName(regObj.getUsername());

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),
                    regObj.getPassword()));

            String token = jwtTokenProvider.createToken(user);
            log.info("token creation passed");
            userService.login(user, token);

            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            log.info("Trace", e);
            return new ResponseEntity<>(e, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping("/validate_token")
    public ValidatedToken validateToken(
            @RequestParam("token") String token) {
        ValidatedToken validatedToken = new ValidatedToken();
        try {
            validatedToken = jwtTokenProvider.validateToken(token);
        } catch (JwtException | BadCredentialsException e) {
            validatedToken.setValidated(false);
            validatedToken.setMessage(e.toString());
            return validatedToken;
        }
        return validatedToken;
    }
}