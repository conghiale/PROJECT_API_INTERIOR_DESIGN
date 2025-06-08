package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 3:45 PM
 */

import lombok.RequiredArgsConstructor;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.dto.TestimonialDTO;
import org.interior_design.service.TestimonialService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/testimonial")
@RequiredArgsConstructor
public class TestimonialController {
    private final TestimonialService testimonialService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getByID(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(testimonialService.getByID(ID));
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllTestimonials(Pageable pageable) {
        return ResponseEntity.ok(testimonialService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<APIResponse> addTestimonial(@RequestBody TestimonialDTO testimonialDTO) {
        return ResponseEntity.ok(testimonialService.create(testimonialDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> editTestimonial(@PathVariable("id") Integer ID, @RequestBody TestimonialDTO testimonialDTO) {
        return ResponseEntity.ok(testimonialService.update(ID, testimonialDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> remove(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(testimonialService.delete(ID));
    }
}
