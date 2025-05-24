package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 3:45 PM
 */

import lombok.RequiredArgsConstructor;
import org.interior_design.dto.APIResponse;
import org.interior_design.model.AuthProvider;
import org.interior_design.service.AuthProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/auth_provider")
@RequiredArgsConstructor
public class AuthProviderController {
    private final AuthProviderService authProviderService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllAuthProviders() {
        return ResponseEntity.ok(authProviderService.getAllAuthProviders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getAuthProviderById(@PathVariable("id") int id) {
        return ResponseEntity.ok(authProviderService.getAuthProviderById(id));
    }

    @PostMapping
    public ResponseEntity<APIResponse> addAuthProvider(@RequestBody AuthProvider authProvider) {
        return ResponseEntity.ok(authProviderService.saveAuthProvider(authProvider));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateAuthProviderById(@PathVariable("id") int id, @RequestBody AuthProvider authProvider) {
        authProvider.setID(id);
        return ResponseEntity.ok(authProviderService.saveAuthProvider(authProvider));
    }
}
