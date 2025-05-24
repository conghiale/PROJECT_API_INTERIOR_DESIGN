package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 11:40 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.SectionDTO;
import org.interior_design.service.SectionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/sections")
@RequiredArgsConstructor
@Log4j2
public class SectionController {
    private final SectionService sectionService;

    @GetMapping("/{code}")
    public ResponseEntity<APIResponse> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(sectionService.getByCode(code));
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAll(Pageable pageable) {
        return ResponseEntity.ok(sectionService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SectionDTO sectionDTO) {
        return ResponseEntity.ok(sectionService.create(sectionDTO));
    }

    @PutMapping("/{code}")
    public ResponseEntity<APIResponse> update(@PathVariable String code, @RequestBody SectionDTO sectionDTO) {
        return ResponseEntity.ok(sectionService.update(code, sectionDTO));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<APIResponse> delete(@PathVariable String code) {
        return ResponseEntity.ok(sectionService.delete(code));
    }
}
