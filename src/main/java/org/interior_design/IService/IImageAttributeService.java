package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 5:18 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.model.ImageAttribute;
import org.interior_design.model.ProjectStatus;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface IImageAttributeService {
    APIResponse getByID(Integer ID);
    APIResponse getByAttrAndType(Integer attr, String type);
    APIResponse getAll(Pageable pageable);
    APIResponse create(ImageAttribute imageAttribute);
    APIResponse update(Integer ID, ImageAttribute imageAttribute);
    APIResponse delete(Integer ID);
}
