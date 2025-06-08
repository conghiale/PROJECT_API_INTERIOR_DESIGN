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
@Table(name = "QUOTE_TYPE")
public class QuoteType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "`NAME`")
    private String name;

    @Column(name = "`DESCRIPTION`")
    private String description;

    @Column(name = "CREATED_AT")
    private LocalDateTime createAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updateAt;
}
