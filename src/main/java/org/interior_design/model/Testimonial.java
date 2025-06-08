package org.interior_design.model;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/14
 * Time: 9:41 PM
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TESTIMONIAL")
public class Testimonial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Min(1)
    @Max(5)
    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "`NAME`")
    private String name;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    private Project project;

    @OneToOne
    @JoinColumn(name = "IMAGE_ID", nullable = false)
    private Image image;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void onUpdate() { updatedAt = LocalDateTime.now(); }
}
