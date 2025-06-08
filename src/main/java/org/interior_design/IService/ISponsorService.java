package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:49 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.dto.SponsorDTO;
import org.interior_design.dto.TestimonialDTO;
import org.interior_design.model.Sponsor;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface ISponsorService {
    APIResponse getByID(Integer ID);
    APIResponse getAll(Pageable pageable);
    APIResponse create(SponsorDTO sponsorDTO);
    APIResponse update(Integer ID, SponsorDTO sponsorDTO);
    APIResponse delete(Integer ID);
}
