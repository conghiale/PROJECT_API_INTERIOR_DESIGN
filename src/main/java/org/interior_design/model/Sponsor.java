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
@Table(name = "SPONSOR")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "`ORDER`")
    private Integer order;

    @Column(name = "`NAME`")
    private String name;

    @Column(name = "IS_ACTIVE")
    private boolean isActive = true;

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
