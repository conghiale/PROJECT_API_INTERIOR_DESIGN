package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 4:52 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.dto.ProjectDTO;
import org.interior_design.service.ProjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/project")
@RequiredArgsConstructor
@Log4j2
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/{slug}")
    public ResponseEntity<APIResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(projectService.getBySlug(slug));
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAll(Pageable pageable) {
        return ResponseEntity.ok(projectService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<APIResponse> addProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.create(projectDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> editProject(@PathVariable("id") Integer ID, @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.update(ID, projectDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> remove(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(projectService.delete(ID));
    }
}
