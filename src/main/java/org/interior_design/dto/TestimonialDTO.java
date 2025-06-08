package org.interior_design.dto;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:43 PM
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.interior_design.model.Image;
import org.interior_design.model.Project;

import java.time.LocalDateTime;

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialDTO {
    private Integer ID;
    private Integer projectId;
    private Integer rating;

    private String name;
    private String content;
    private String projectTitle;
    private String imageBase64;
}
