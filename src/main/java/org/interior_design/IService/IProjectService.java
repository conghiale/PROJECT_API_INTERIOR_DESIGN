package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:49 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.dto.ProjectDTO;
import org.interior_design.dto.SectionDTO;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface IProjectService {
    APIResponse getBySlug(String slug);
    APIResponse getAll(Pageable pageable);
    APIResponse create(ProjectDTO projectDTO);
    APIResponse update(Integer ID, ProjectDTO projectDTO);
    APIResponse delete(Integer ID);
}
