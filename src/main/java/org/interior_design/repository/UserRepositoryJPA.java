package org.interior_design.repository;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/10
 * Time: 4:03 PM
 */

import org.interior_design.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
