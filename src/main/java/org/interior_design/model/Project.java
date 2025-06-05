package org.interior_design.model;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 4:46 PM
 */

import jakarta.persistence.*;
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
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "SLUG", unique = true, nullable = false)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "INVESTOR")
    private String investor;

    @Column(name = "ACREAGE")
    private String acreage;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DESIGNER")
    private String designer;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "FACTORY")
    private String factory;

    @Column(name = "HOTLINE")
    private String hotline;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WEBSITE")
    private String website;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_STATUS_ID")
    private ProjectStatus projectStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
