package br.com.fiap.techhealth.infraestructure.security.service;

import br.com.fiap.techhealth.domain.model.Role;
import br.com.fiap.techhealth.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.user.getRole().equals(Role.DOCTOR)) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_DOCTOR"),
                    new SimpleGrantedAuthority("ROLE_NURSE"),
                    new SimpleGrantedAuthority("ROLE_PATIENT")
            );
        } else if (this.user.getRole().equals(Role.NURSE)) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_NURSE"),
                    new SimpleGrantedAuthority("ROLE_PATIENT")
            );
        } else return List.of(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
