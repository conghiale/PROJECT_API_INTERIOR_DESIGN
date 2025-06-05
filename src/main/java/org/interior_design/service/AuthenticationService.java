package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 2:57 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.TokenPair;
import org.interior_design.dto.UserDTO;
import org.interior_design.model.AuthProvider;
import org.interior_design.model.Role;
import org.interior_design.model.User;
import org.interior_design.repository.AuthProviderRepositoryJPA;
import org.interior_design.repository.UserRepositoryJPA;
import org.interior_design.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {
    private final UserRepositoryJPA userRepositoryJPA;
    private final AuthProviderRepositoryJPA authProviderRepositoryJPA;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public APIResponse register(UserDTO userDTO) {
        try {
            AuthProvider authProvider = authProviderRepositoryJPA.findAuthProviderByID(userDTO.getAuthProviderID())
                    .orElseThrow(() -> new UsernameNotFoundException("Auth provider not found"));

            var user = User.builder()
                    .username(userDTO.getUsername())
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .email(userDTO.getEmail())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .avatarBase64(userDTO.getAvatarBase64())
                    .avatarURL(userDTO.getAvatarURL())
                    .authProvider(authProvider)
                    .isAdmin(userDTO.getIsAdmin())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .role(userDTO.getIsAdmin() ? Role.ADMIN : Role.USER)
                    .build();

            userRepositoryJPA.save(user);
            TokenPair tokenPair = jwtService.generateTokenPair(new HashMap<>(), user);
            return APIResponse.builder()
                    .code(0)
                    .message("Successfully registered")
                    .tokenPair(tokenPair)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK REGISTER] Error registering user: {}", Utils.printStackTrace(e));
            return APIResponse.builder()
                    .code(1)
                    .message("Error registering user")
                    .build();
        }
    }

    public APIResponse authenticate(UserDTO userDTO) {
//        Authentication when client enter username/password to login
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            var user = userRepositoryJPA.findByUsername(userDTO.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            TokenPair tokenPair = jwtService.generateTokenPair(new HashMap<>(), user);
            return APIResponse.builder()
                    .code(0)
                    .message("Successfully authenticated")
                    .tokenPair(tokenPair)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK AUTHENTICATE] Error authenticate: {}", e.getMessage());
            return APIResponse.builder()
                    .code(1)
                    .message("Error authenticate")
                    .build();
        }
    }

    public APIResponse getTokenPair(UserDTO userDTO) {
//        Authentication when client enter refreshToken
        try {
            final String username;
            if (Utils.isNullOrEmpty(userDTO.getRefreshToken())) {
                throw new RuntimeException("Refresh token is empty");
            }

            String jwt = userDTO.getRefreshToken();
            username = jwtService.extractUsername(jwt);
            if (username == null) {
                throw new RuntimeException("Invalid refresh token");
            }

            var user = userRepositoryJPA.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            TokenPair tokenPair = jwtService.generateTokenPair(new HashMap<>(), user);
            return APIResponse.builder()
                    .code(0)
                    .message("Get new token pair successfully")
                    .tokenPair(tokenPair)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET TOKEN PAIR] Error getting token pair: {}", Utils.printStackTrace(e));
            return APIResponse.builder()
                    .code(1)
                    .message("Error getting token pair")
                    .build();
        }
    }
}
