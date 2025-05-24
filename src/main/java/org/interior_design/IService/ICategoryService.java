package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:49 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.dto.SectionDTO;
import org.interior_design.model.Category;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface ICategoryService {
    APIResponse getBySlug(String slug);
    APIResponse getAll(Pageable pageable);
    APIResponse create(CategoryDTO categoryDTO);
    APIResponse update(String slug, CategoryDTO categoryDTO);
    APIResponse delete(String slug);
}
