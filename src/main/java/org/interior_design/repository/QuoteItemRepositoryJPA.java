package org.interior_design.repository;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/10
 * Time: 4:03 PM
 */

import org.interior_design.model.Quote;
import org.interior_design.model.QuoteItem;
import org.interior_design.model.QuoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface QuoteItemRepositoryJPA extends JpaRepository<QuoteItem, Integer> {
    Optional<QuoteItem> findByID(Integer ID);
    List<QuoteItem> findByQuoteID(Integer quote_ID);
    Page<QuoteItem> findAll(Pageable pageable);
}
