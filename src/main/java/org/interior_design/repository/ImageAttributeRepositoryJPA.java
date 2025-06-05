package org.interior_design.repository;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 5:10 PM
 */

import org.interior_design.model.ImageAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface ImageAttributeRepositoryJPA extends JpaRepository<ImageAttribute, Integer> {
    List<ImageAttribute> findByAttrIdAndType(Integer attrId, Integer type);
    Optional<ImageAttribute> findByAttrIdAndTypeAndIsPrimaryTrue(Integer attrId, Integer type);

    @Transactional
    void deleteByAttrIdAndType(Integer attrId, Integer type);
}
