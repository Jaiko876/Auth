package qas.auth.auth.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import qas.auth.auth.dto.RoleDto;
import qas.auth.auth.dto.Status;
import qas.auth.auth.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    public  JwtUserFactory() {}
    public static JwtUser create(UserDto userDto) {
        return new JwtUser(userDto.getId(),userDto.getLogin(),
                userDto.getPassword(),userDto.getToken(),userDto.getStatus(),
                userDto.getRoles(), true,
                mapToGrantedAuthories(new ArrayList<>(userDto.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthories(List<RoleDto> roles) {
        return roles.stream().map(roleDto -> new SimpleGrantedAuthority(roleDto.getName()))
                .collect(Collectors.toList());
    }
}
