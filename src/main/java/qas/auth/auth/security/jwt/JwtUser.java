package qas.auth.auth.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import qas.auth.auth.dto.RoleDto;
import qas.auth.auth.dto.Status;
import qas.auth.auth.dto.UserDto;

import java.util.Collection;
import java.util.List;

public class JwtUser extends UserDto implements UserDetails {
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(long id, String login, String password, String token, Status status,
                   List<RoleDto> roles, boolean enabled,
                   Collection<? extends GrantedAuthority> authorities) {
        super(id, login, password, token, status, roles);
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
