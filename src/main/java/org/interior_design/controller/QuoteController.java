package org.interior_design.controller;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 3:45 PM
 */

import lombok.RequiredArgsConstructor;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.QuoteDTO;
import org.interior_design.dto.TestimonialDTO;
import org.interior_design.service.QuoteService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/v2/quote")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getByID(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(quoteService.getByID(ID));
    }

    @GetMapping("/type/{typeID}")
    public ResponseEntity<APIResponse> getByQuoteType(@PathVariable("typeID") Integer typeID) {
        return ResponseEntity.ok(quoteService.getByQuoteType(typeID));
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllQuotes(Pageable pageable) {
        return ResponseEntity.ok(quoteService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<APIResponse> addQuote(@RequestBody QuoteDTO quoteDTO) {
        return ResponseEntity.ok(quoteService.create(quoteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> editQuote(@PathVariable("id") Integer ID, @RequestBody QuoteDTO quoteDTO) {
        return ResponseEntity.ok(quoteService.update(ID, quoteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> remove(@PathVariable("id") Integer ID) {
        return ResponseEntity.ok(quoteService.delete(ID));
    }
}
