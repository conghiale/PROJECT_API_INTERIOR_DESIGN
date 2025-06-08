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
import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUOTE")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SUBTITLE")
    private String subtitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUOTE_TYPE_ID", nullable = false)
    private QuoteType quoteType;

    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuoteItem> quoteItems;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void onUpdate() { updatedAt = LocalDateTime.now(); }
}
