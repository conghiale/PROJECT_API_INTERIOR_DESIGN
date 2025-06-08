package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 3:45 PM
 */

import lombok.RequiredArgsConstructor;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{slug}")
    public ResponseEntity<APIResponse> getBySlug(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(categoryService.getBySlug(slug));
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<APIResponse> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> editCategory(@PathVariable("id") Integer ID, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.update(ID, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> remove(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(categoryService.delete(ID));
    }
}
