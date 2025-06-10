package org.interior_design.model;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/14
 * Time: 9:41 PM
 */

import jakarta.persistence.*;
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
@Entity
@Table(name = "PROJECT_INFO")
public class ProjectInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "TYPICAL_PROJECT_TITLE")
    private String typicalProjectTitle;

    @Column(name = "TYPICAL_PROJECT_SUB_TITLE")
    private String typicalProjectSubTitle;

    @Column(name = "FEATURED_PROJECT_TITLE")
    private String featuredProjectTitle;

    @Column(name = "FEATURED_PROJECT_SUB_TITLE")
    private String featuredProjectSubTitle;

    @OneToMany(mappedBy = "projectInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectInfoItem> projectInfoItems;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void onUpdate() { updatedAt = LocalDateTime.now(); }
}
