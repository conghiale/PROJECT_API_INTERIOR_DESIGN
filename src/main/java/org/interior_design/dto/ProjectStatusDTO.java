package org.interior_design.dto;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:43 PM
 */

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusDTO {
    private Integer ID;

    private String name;
    private String remarkEn;
    private String remark;
}
