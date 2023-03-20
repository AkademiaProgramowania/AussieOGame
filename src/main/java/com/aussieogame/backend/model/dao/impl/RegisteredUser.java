package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUser extends Basic implements UserDetails {
    @Column(unique = true)
    private String username; //used in Spring Security

    private String displayName; //"username" is semi-reserved by Spring Security to mean a unique Principal id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Authorities should be extracted from JWT in a filter
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return ""; //no passwords in this setup
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
