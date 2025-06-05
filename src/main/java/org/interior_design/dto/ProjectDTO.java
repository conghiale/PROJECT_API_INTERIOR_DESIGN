package org.interior_design.dto;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 4:48 PM
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
public class ProjectDTO {
    private Integer ID;
    private Integer indexPrimaryImage;

    private String slug;
    private String title;
    private String investor;
    private String acreage;
    private String description;
    private String designer;
    private String location;
    private String factory;
    private String hotline;
    private String email;
    private String website;

    private ProjectStatusDTO projectStatusDTO;

    private List<String> images;
}
