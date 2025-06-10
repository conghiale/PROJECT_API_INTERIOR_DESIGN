package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 3:45 PM
 */

import lombok.RequiredArgsConstructor;
import org.interior_design.IService.IProjectInfoService;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.ProjectInfoDTO;
import org.interior_design.dto.QuoteDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/project-info")
@RequiredArgsConstructor
public class ProjectInfoController {
    private final IProjectInfoService projectInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getByID(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(projectInfoService.getByID(ID));
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllTestimonials() {
        return ResponseEntity.ok(projectInfoService.getAll());
    }

    @PostMapping
    public ResponseEntity<APIResponse> addTestimonial(@RequestBody ProjectInfoDTO projectInfoDTO) {
        return ResponseEntity.ok(projectInfoService.create(projectInfoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> editTestimonial(@PathVariable("id") Integer ID, @RequestBody ProjectInfoDTO projectInfoDTO) {
        return ResponseEntity.ok(projectInfoService.update(ID, projectInfoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> remove(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(projectInfoService.delete(ID));
    }
}
