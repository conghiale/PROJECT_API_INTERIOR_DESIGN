package org.interior_design.IService;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:49 PM
 */

import org.interior_design.dto.APIResponse;
import org.interior_design.dto.QuoteDTO;
import org.interior_design.dto.TestimonialDTO;
import org.springframework.data.domain.Pageable;

/**
 * @ 2025. All rights reserved
 */

public interface IQuoteService {
    APIResponse getByID(Integer ID);
    APIResponse getAll(Pageable pageable);
    APIResponse create(QuoteDTO quoteDTO);
    APIResponse update(Integer ID, QuoteDTO quoteDTO);
    APIResponse delete(Integer ID);
}
