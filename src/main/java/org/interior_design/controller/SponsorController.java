package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 3:45 PM
 */

import lombok.RequiredArgsConstructor;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.dto.SponsorDTO;
import org.interior_design.service.SponsorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/sponsor")
@RequiredArgsConstructor
public class SponsorController {
    private final SponsorService sponsorService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getByID(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(sponsorService.getByID(ID));
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllSponsors(Pageable pageable) {
        return ResponseEntity.ok(sponsorService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<APIResponse> addSponsor(@RequestBody SponsorDTO sponsorDTO) {
        return ResponseEntity.ok(sponsorService.create(sponsorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> editSponsor(@PathVariable("id") Integer ID, @RequestBody SponsorDTO sponsorDTO) {
        return ResponseEntity.ok(sponsorService.update(ID, sponsorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> remove(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(sponsorService.delete(ID));
    }
}
