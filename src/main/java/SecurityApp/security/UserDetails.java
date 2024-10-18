package SecurityApp.security;

import SecurityApp.models.Role;
import SecurityApp.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final User person;

    public UserDetails(User user) {
        this.person = user;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> auths = person.getAuths();
        return auths.stream().map(r -> new SimpleGrantedAuthority(r.getAuthority())).collect(Collectors.toSet());
    }


    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getName();
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
        return true;
    }

    // Нужно, чтобы получать данные аутентифицированного пользователя
    public User getPerson() {
        return this.person;
    }


}
