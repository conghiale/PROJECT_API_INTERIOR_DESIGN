package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 5:18 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.dto.ProjectDTO;
import org.interior_design.model.ProjectStatus;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface IProjectStatusService {
    APIResponse getByID(Integer ID);
    APIResponse getAll(Pageable pageable);
    APIResponse create(ProjectStatus projectStatus);
    APIResponse update(Integer ID, ProjectStatus projectStatus);
    APIResponse delete(Integer ID);
}
