package org.interior_design.model;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 5:03 PM
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
@Table(name = "IMAGE_ATTRIBUTE")  // Make sure this matches exactly with DB table name
public class ImageAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_ID")
    private Image image;

    @Column(name = "ATTR_ID", nullable = false)
    private Integer attrId;      // Property name must be attrId, not ATTR_ID

    @Column(name = "TYPE")
    private Integer type = 1;    // Property name must be type, not TYPE

    @Column(name = "IS_PRIMARY")
    private Boolean isPrimary = false;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
