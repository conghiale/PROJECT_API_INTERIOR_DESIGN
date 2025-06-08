package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:49 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.dto.TestimonialDTO;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface ITestimonialService {
    APIResponse getByID(Integer ID);
    APIResponse getAll(Pageable pageable);
    APIResponse create(TestimonialDTO testimonialDTO);
    APIResponse update(Integer ID, TestimonialDTO testimonialDTO);
    APIResponse delete(Integer ID);
}
