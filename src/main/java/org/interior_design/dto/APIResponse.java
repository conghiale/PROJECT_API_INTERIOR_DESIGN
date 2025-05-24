package org.interior_design.dto;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/11
 * Time: 2:26 PM
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ 2025. All rights reserved
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    private Integer code;
    private String message;
    private TokenPair tokenPair;
    private Object data;
}
