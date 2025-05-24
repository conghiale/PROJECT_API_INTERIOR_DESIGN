package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:19 PM
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.dto.APIResponse;
import org.interior_design.model.AuthProvider;
import org.interior_design.repository.AuthProviderRepositoryJPA;
import org.interior_design.utils.Utils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthProviderService {
    private final AuthProviderRepositoryJPA authProviderRepositoryJPA;
    private final ObjectMapper objectMapper;

    public APIResponse getAuthProviderById(int id) {
        APIResponse apiResponse;
        try {
            AuthProvider authProvider = authProviderRepositoryJPA.findAuthProviderByID(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Auth provider with id " + id + " not found"));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Get auth provider successfully")
                    .data(authProvider)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET AUTH PROVIDER BY ID] Error while getting auth provider by id {}. Details: {}", id, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Error while getting auth provider by id")
                    .build();
        }

        return apiResponse;
    }

    public APIResponse getAllAuthProviders() {
        APIResponse apiResponse;
        try {
            List<AuthProvider> authProviders = authProviderRepositoryJPA.findAll();

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Get all auth provider successfully")
                    .data(authProviders.isEmpty() ? "" : authProviders)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET ALL AUTH PROVIDER] Error while getting all auth provider. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Error while getting all auth provider")
                    .build();
        }

        return apiResponse;
    }

    public APIResponse saveAuthProvider(AuthProvider authProvider) {
        APIResponse apiResponse;
        try {
            AuthProvider authProviderRes = authProviderRepositoryJPA.save(authProvider);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Get save auth provider successfully")
                    .data(objectMapper.writeValueAsString(authProviderRes.getID()))
                    .build();

        } catch (Exception e) {
            log.error("[CHECK SAVE AUTH PROVIDER] Error while add auth provider. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Error while save auth provider")
                    .build();
        }

        return apiResponse;
    }
}
