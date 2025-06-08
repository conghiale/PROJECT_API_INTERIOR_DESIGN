package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:19 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.ICategoryService;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.model.Category;
import org.interior_design.repository.CategoryRepositoryJPA;
import org.interior_design.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService implements ICategoryService {
    private final CategoryRepositoryJPA categoryRepositoryJPA;

    @Override
    public APIResponse getBySlug(String slug) {
        APIResponse apiResponse;
        try {
            Category category = categoryRepositoryJPA.findCategoriesBySlug(slug)
                    .orElseThrow(() -> new UsernameNotFoundException("Category with slug " + slug + " not found"));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Category with Slug " + slug + " found")
                    .data(category)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET BY SLUG] Error while getting category by slug {}. Details: {}", slug, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Category with Slug " + slug + " not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        APIResponse apiResponse;
        try {
            Page<CategoryDTO> categoryDTOPage = categoryRepositoryJPA.findAll(pageable).map(this::toDTO);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Category found")
                    .data(categoryDTOPage)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error while getting all categories. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Category not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse create(CategoryDTO categoryDTO) {
        APIResponse apiResponse;
        try {
            categoryDTO = toDTO(categoryRepositoryJPA.save(fromDto(categoryDTO)));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Category created")
                    .data(categoryDTO)
                    .build();

        } catch (Exception e) {
            log.error("[CHECK CREATE] Error while add category. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Create category failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, CategoryDTO categoryDTO) {
        APIResponse apiResponse;
        try {
            Category category = categoryRepositoryJPA.findCategoriesByID(ID)
                    .orElseThrow(() -> new UsernameNotFoundException("Category with ID " + ID + " not found"));
            Category categoryToUpdate = fromDto(categoryDTO);
            categoryToUpdate.setID(category.getID());

            categoryDTO = toDTO(categoryRepositoryJPA.save(categoryToUpdate));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Category updated")
                    .data(categoryDTO)
                    .build();

        } catch (Exception e) {
            log.error("[CHECK UPDATE] Error while update category with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Updated category failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse;
        try {
            Category category = categoryRepositoryJPA.findCategoriesByID(ID)
                    .orElseThrow(() -> new UsernameNotFoundException("Category with ID " + ID + " not found"));

            categoryRepositoryJPA.delete(category);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Category deleted")
                    .data(toDTO(category))
                    .build();

        } catch (Exception e) {
            log.error("[CHECK DELETE] Error while delete category with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Delete category failed")
                    .build();
        }

        return apiResponse;
    }

    private CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setID(category.getID());
        categoryDTO.setName(category.getName());
        categoryDTO.setSlug(category.getSlug());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    private Category fromDto(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setCreateAt(LocalDateTime.now());
        category.setUpdateAt(LocalDateTime.now());
        return category;
    }
}
