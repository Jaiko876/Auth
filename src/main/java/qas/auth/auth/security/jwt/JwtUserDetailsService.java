package qas.auth.auth.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import qas.auth.auth.dto.UserDto;
import qas.auth.auth.services.iService.IUserService;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService{
    private final IUserService userSevice;

    public JwtUserDetailsService(@Lazy IUserService userSevice) {
        this.userSevice = userSevice;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDto userDto = userSevice.findByUserName(userName);
        if (userDto == null) {
            throw  new UsernameNotFoundException("User with username "
            + userName + " not found");
        }

        return JwtUserFactory.create(userDto);
    }
}
