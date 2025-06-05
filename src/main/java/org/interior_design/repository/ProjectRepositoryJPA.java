package org.interior_design.repository;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 4:48 PM
 */

import org.interior_design.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface ProjectRepositoryJPA extends JpaRepository<Project, Integer> {
    Optional<Project> findBySlug(String slug);
    Optional<Project> findByID(Integer ID);
    boolean existsBySlug(String slug);
}
