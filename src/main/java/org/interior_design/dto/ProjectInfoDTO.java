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
public class ProjectInfoDTO {
    private Integer ID;

    private String typicalProjectTitle;
    private String typicalProjectSubTitle;
    private String featuredProjectTitle;
    private String featuredProjectSubTitle;

    private List<ProjectInfoItemDTO> projectInfoItems;
}
