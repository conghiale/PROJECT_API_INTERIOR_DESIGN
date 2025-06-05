package org.interior_design.model;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/10
 * Time: 2:36 PM
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PASSWORD_HASH")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "AVATAR_URL")
    private String avatarURL;

    @Column(name = "AVATAR_BASE64")
    private String avatarBase64;

    @Column(name = "IS_ADMIN")
    private Boolean isAdmin;

    @Column(name = "IS_ENABLE")
    @Builder.Default
    private Boolean isEnable = true;

    @Column(name = "IS_NON_EXPIRED")
    @Builder.Default
    private Boolean isNonExpired = true;

    @Column(name = "IS_CREDENTIALS_NON_EXPIRED")
    @Builder.Default
    private Boolean isCredentialsNonExpired = true;

    @Column(name = "IS_NON_LOCKED")
    @Builder.Default
    private Boolean isNonLocked = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTH_PROVIDER_ID")
    private AuthProvider authProvider;

    @Column(name = "CREATED_AT")
    private LocalDateTime createAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String  getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
