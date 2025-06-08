package org.interior_design.dto;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:43 PM
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SponsorDTO {
    private Integer ID;
    private String name;
    private Integer order;
    private Boolean isActive;
    private String imageBase64;
}
