package org.interior_design.dto;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:43 PM
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDTO {
    private Integer ID;
    private Integer quoteTypeId;

    private String title;
    private String subtitle;
    private String quoteTypeName;
    private List<QuoteItemDTO> quoteItems;
}
