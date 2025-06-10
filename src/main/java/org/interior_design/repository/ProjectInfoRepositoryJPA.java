package org.interior_design.repository;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/10
 * Time: 4:03 PM
 */

import org.interior_design.model.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface ProjectInfoRepositoryJPA extends JpaRepository<ProjectInfo, Integer> {
    Optional<ProjectInfo> findByID(Integer ID);
    Page<ProjectInfo> findAll(Pageable pageable);
}
