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

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROJECT_INFO_ITEM")
public class ProjectInfoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "ITEM_TITLE", nullable = false)
    private String itemTitle;

    @Column(name = "ITEM_NUMBER", nullable = false)
    private String itemNumber;

    @Column(name = "REMARK_EN")
    private String remarkEn;

    @Column(name = "REMARK")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "PROJECT_INFO_ID", nullable = false)
    private ProjectInfo projectInfo;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void onUpdate() { updatedAt = LocalDateTime.now(); }
}
