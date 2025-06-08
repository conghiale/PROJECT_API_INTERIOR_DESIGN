package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:19 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.ISponsorService;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.CategoryDTO;
import org.interior_design.dto.SponsorDTO;
import org.interior_design.dto.TestimonialDTO;
import org.interior_design.model.Category;
import org.interior_design.model.Image;
import org.interior_design.model.Sponsor;
import org.interior_design.repository.ImageRepositoryJPA;
import org.interior_design.repository.SponsorRepositoryJPA;
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
public class SponsorService implements ISponsorService {
    private final SponsorRepositoryJPA sponsorRepositoryJPA;
    private final ImageRepositoryJPA imageRepositoryJPA;

    @Override
    public APIResponse getByID(Integer ID) {
        APIResponse apiResponse;
        try {
            Sponsor sponsor = sponsorRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new UsernameNotFoundException("Sponsor with id " + ID + " not found"));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Sponsor with id " + ID + " found")
                    .data(toDTO(sponsor))
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET BY SLUG] Error while getting sponsor by id {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Sponsor with id " + ID + " not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        APIResponse apiResponse;
        try {
            Page<SponsorDTO> sponsorDTOPage = sponsorRepositoryJPA.findAll(pageable).map(this::toDTO);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Sponsor found")
                    .data(sponsorDTOPage)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error while getting all sponsors. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Sponsor not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse create(SponsorDTO sponsorDTO) {
        APIResponse apiResponse;
        try {
            sponsorDTO = toDTO(sponsorRepositoryJPA.save(saveFromDTO(sponsorDTO)));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("sponsor created")
                    .data(sponsorDTO)
                    .build();

        } catch (Exception e) {
            log.error("[CHECK CREATE] Error while add sponsor. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Create sponsor failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, SponsorDTO sponsorDTO) {
        APIResponse apiResponse;
        try {
            Sponsor sponsor = sponsorRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new UsernameNotFoundException("Sponsor with ID " + ID + " not found"));
            Sponsor sponsorToUpdate = updateFromDTO(sponsor, sponsorDTO);
            sponsorToUpdate.setID(sponsor.getID());

            sponsorDTO = toDTO(sponsorRepositoryJPA.save(sponsorToUpdate));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Sponsor updated")
                    .data(sponsorDTO)
                    .build();

        } catch (Exception e) {
            log.error("[CHECK UPDATE] Error while update sponsor with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Updated sponsor failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse;
        try {
            Sponsor sponsor = sponsorRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new UsernameNotFoundException("Sponsor with ID " + ID + " not found"));

            sponsorRepositoryJPA.delete(sponsor);
            imageRepositoryJPA.delete(sponsor.getImage());

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Sponsor deleted")
                    .data(toDTO(sponsor))
                    .build();

        } catch (Exception e) {
            log.error("[CHECK DELETE] Error while delete sponsor with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Delete sponsor failed")
                    .build();
        }

        return apiResponse;
    }

    private SponsorDTO toDTO(Sponsor sponsor) {
        SponsorDTO dto = new SponsorDTO();
        dto.setID(sponsor.getID());
        dto.setName(sponsor.getName());
        dto.setOrder(sponsor.getOrder());
        dto.setIsActive(sponsor.isActive());
        dto.setImageBase64(sponsor.getImage().getImageBase64());
        return dto;
    }

    private Sponsor fromDTO(SponsorDTO dto) {
        Sponsor sponsor = new Sponsor();
        return updateFromDTO(sponsor, dto);
    }

    private Sponsor updateFromDTO(Sponsor sponsor, SponsorDTO dto) {
        sponsor.setName(dto.getName());
        sponsor.setOrder(dto.getOrder());
        sponsor.setActive(dto.getIsActive());

        Image image = imageRepositoryJPA.findByID(sponsor.getImage().getID())
                .orElseThrow(() -> new RuntimeException("Image not found with ID: " + sponsor.getImage().getID()));
        image.setImageBase64(dto.getImageBase64());
        image = imageRepositoryJPA.save(image);
        sponsor.setImage(image);

        return sponsor;
    }

    private Sponsor saveFromDTO(SponsorDTO dto) {
        Sponsor sponsor = new Sponsor();
        sponsor.setName(dto.getName());
        sponsor.setOrder(dto.getOrder());
        sponsor.setActive(dto.getIsActive());

        Image image = new Image();
        image.setImageBase64(dto.getImageBase64());
        image = imageRepositoryJPA.save(image);
        sponsor.setImage(image);

        return sponsor;
    }
}
