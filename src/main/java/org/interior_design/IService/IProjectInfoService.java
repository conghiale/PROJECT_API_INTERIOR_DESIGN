package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:49 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.dto.ProjectInfoDTO;
import org.interior_design.dto.QuoteDTO;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface IProjectInfoService {
    APIResponse getByID(Integer ID);
    APIResponse getAll();
    APIResponse create(ProjectInfoDTO projectInfoDTO);
    APIResponse update(Integer ID, ProjectInfoDTO projectInfoDTO);
    APIResponse delete(Integer ID);
}
