package org.interior_design.repository;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:47 PM
 */

import org.interior_design.model.Image;
import org.interior_design.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface ImageRepositoryJPA extends JpaRepository<Image, Integer> {
    Optional<Image> findByID(Integer ID);
}
