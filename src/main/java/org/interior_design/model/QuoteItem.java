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
@Table(name = "QUOTE_ITEM")
public class QuoteItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "PRICE", nullable = false)
    private String price;

    @ManyToOne
    @JoinColumn(name = "QUOTE_ID", nullable = false)
    private Quote quote;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void onUpdate() { updatedAt = LocalDateTime.now(); }
}
