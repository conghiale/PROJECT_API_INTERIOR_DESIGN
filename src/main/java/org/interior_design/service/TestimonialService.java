package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:19 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.ICategoryService;
import org.interior_design.IService.ITestimonialService;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.dto.TestimonialDTO;
import org.interior_design.model.Category;
import org.interior_design.model.Image;
import org.interior_design.model.Project;
import org.interior_design.model.Testimonial;
import org.interior_design.repository.ImageRepositoryJPA;
import org.interior_design.repository.ProjectRepositoryJPA;
import org.interior_design.repository.TestimonialRepositoryJPA;
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
public class TestimonialService implements ITestimonialService {
    private final TestimonialRepositoryJPA testimonialRepositoryJPA;
    private final ProjectRepositoryJPA projectRepositoryJPA;
    private final ImageRepositoryJPA imageRepositoryJPA;

    @Override
    public APIResponse getByID(Integer ID) {
        APIResponse apiResponse;
        try {
            Testimonial testimonial = testimonialRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Testimonial with id " + ID + " not found"));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Testimonial with id " + ID + " found")
                    .data(toDTO(testimonial))
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET BY ID] Error while getting testimonial by id {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Testimonial with id " + ID + " not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        APIResponse apiResponse;
        try {
            Page<TestimonialDTO> testimonialDTOPage = testimonialRepositoryJPA.findAll(pageable).map(this::toDTO);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Testimonial found")
                    .data(testimonialDTOPage)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error while getting all testimonials. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Testimonial not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse create(TestimonialDTO testimonialDTO) {
        APIResponse apiResponse;
        try {
            testimonialDTO = toDTO(testimonialRepositoryJPA.save(saveFromDTO(testimonialDTO)));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Testimonial created")
                    .data(testimonialDTO)
                    .build();

        } catch (Exception e) {
            log.error("[CHECK CREATE] Error while add testimonial. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Create testimonial failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, TestimonialDTO testimonialDTO) {
        APIResponse apiResponse;
        try {
            Testimonial testimonial = testimonialRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Testimonial with ID " + ID + " not found"));
            Testimonial testimonialToUpdate = updateFromDTO(testimonial, testimonialDTO);
            testimonialToUpdate.setID(testimonial.getID());

            testimonialDTO = toDTO(testimonialRepositoryJPA.save(testimonialToUpdate));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Testimonial updated")
                    .data(testimonialDTO)
                    .build();

        } catch (Exception e) {
            log.error("[CHECK UPDATE] Error while update testimonial with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Updated testimonial failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse;
        try {
            Testimonial testimonial = testimonialRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Testimonial with ID " + ID + " not found"));

            testimonialRepositoryJPA.delete(testimonial);
            imageRepositoryJPA.delete(testimonial.getImage());

//            Builder does not serialize from a deleted implementation. Use dto
            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Testimonial deleted")
                    .data(toDTO(testimonial))
                    .build();

        } catch (Exception e) {
            log.error("[CHECK DELETE] Error while delete testimonial with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Delete testimonial failed")
                    .build();
        }

        return apiResponse;
    }

    private TestimonialDTO toDTO(Testimonial testimonial) {
        TestimonialDTO dto = new TestimonialDTO();
        dto.setID(testimonial.getID());
        dto.setName(testimonial.getName());
        dto.setContent(testimonial.getContent());
        dto.setRating(testimonial.getRating());
        dto.setProjectId(testimonial.getProject().getID());
        dto.setProjectTitle(testimonial.getProject().getTitle());
        dto.setImageBase64(testimonial.getImage().getImageBase64());
        return dto;
    }

    private Testimonial fromDTO(TestimonialDTO dto) {
        Testimonial testimonial = new Testimonial();
        return updateFromDTO(testimonial, dto);
    }

    private Testimonial updateFromDTO(Testimonial testimonial, TestimonialDTO dto) {
        testimonial.setName(dto.getName());
        testimonial.setContent(dto.getContent());
        testimonial.setRating(dto.getRating());

        Project project = projectRepositoryJPA.findByID(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + dto.getProjectId()));
        testimonial.setProject(project);

        Image image = imageRepositoryJPA.findByID(testimonial.getImage().getID())
                .orElseThrow(() -> new RuntimeException("Image not found with ID: " + testimonial.getImage().getID()));
        image.setImageBase64(dto.getImageBase64());
        image = imageRepositoryJPA.save(image);
        testimonial.setImage(image);

        return testimonial;
    }

    private Testimonial saveFromDTO(TestimonialDTO dto) {
        Testimonial testimonial = new Testimonial();
        testimonial.setName(dto.getName());
        testimonial.setContent(dto.getContent());
        testimonial.setRating(dto.getRating());

        Project project = projectRepositoryJPA.findByID(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + dto.getProjectId()));
        testimonial.setProject(project);

        Image image = new Image();
        image.setImageBase64(dto.getImageBase64());
        image = imageRepositoryJPA.save(image);
        testimonial.setImage(image);

        return testimonial;
    }
}
