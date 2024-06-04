package br.com.estudos.estudosdocker.helpers;


import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.domain.UserRole;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails extends UserInfo implements UserDetails {

    @Transient
    private final String username;
    @Transient
    private final String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(final UserInfo byUsername) {
        this.username = byUsername.getUsername();
        this.password = byUsername.getPassword();
        final var auths = new ArrayList<GrantedAuthority>();

        for (final UserRole role : byUsername.getRoles()) {

            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}
