package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/21
 * Time: 10:51 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.ISectionService;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.SectionDTO;
import org.interior_design.dto.SectionItemDTO;
import org.interior_design.model.Image;
import org.interior_design.model.Section;
import org.interior_design.model.SectionItem;
import org.interior_design.model.SectionType;
import org.interior_design.repository.ImageRepositoryJPA;
import org.interior_design.repository.SectionRepositoryJPA;
import org.interior_design.repository.SectionTypeRepositoryJPA;
import org.interior_design.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class SectionService implements ISectionService {

    private final SectionRepositoryJPA sectionRepository;
    private final SectionTypeRepositoryJPA sectionTypeRepository;
    private final ImageRepositoryJPA imageRepository;

    @Override
    public APIResponse getByCode(String code) {
        APIResponse apiResponse = new APIResponse();

        try {
            Section section = sectionRepository.findByCode(code)
                    .orElseThrow(() -> new RuntimeException("Section not found with code " + code));

            SectionDTO sectionDTO = toDTO(section);

            apiResponse.setData(sectionDTO);
            apiResponse.setCode(0);
            apiResponse.setMessage("Section found with code " + code);
        } catch (Exception e) {
            log.error("[CHECK GET BY CODE] section not found with code {}", code);
            apiResponse.setCode(0);
            apiResponse.setMessage("Section not found with code " + code);
        }

        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        APIResponse apiResponse = new APIResponse();

        try {
            Page<SectionDTO> sectionDTOPage = sectionRepository.findAll(pageable).map(this::toDTO);

            apiResponse.setData(sectionDTOPage);
            apiResponse.setCode(0);
            apiResponse.setMessage("Section found");
        } catch (Exception e) {
            log.error("[CHECK GET ALL] section not found");
            apiResponse.setCode(1);
            apiResponse.setMessage("Section not found");
        }

        return apiResponse;
    }

    @Override
    public APIResponse create(SectionDTO sectionDTO) {
        APIResponse apiResponse = new APIResponse();

        try {
            Section section = fromDto(sectionDTO);
            sectionDTO = toDTO(sectionRepository.save(section));

            apiResponse.setData(sectionDTO);
            apiResponse.setCode(0);
            apiResponse.setMessage("Section created");
        } catch (Exception e) {
            log.error("[CHECK CREATE] Add section failed. Details: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Create section failed");
        }

        return apiResponse;
    }

    @Override
    public APIResponse update(String code, SectionDTO sectionDTO) {
        APIResponse apiResponse = new APIResponse();

        try {
            Section section = sectionRepository.findByCode(code)
                    .orElseThrow(() -> new RuntimeException("Section not found with code " + code));
            Section sectionToUpdate = fromDto(sectionDTO);
            sectionToUpdate.setID(section.getID());

            sectionDTO = toDTO(sectionRepository.save(sectionToUpdate));

            apiResponse.setData(sectionDTO);
            apiResponse.setCode(0);
            apiResponse.setMessage("Section updated");
        } catch (Exception e) {
            log.error("[CHECK UPDATE] Error while update section with code {}. Details: {}", code, Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Updated section failed");
        }

        return apiResponse;
    }

    @Override
    public APIResponse delete(String code) {
        APIResponse apiResponse = new APIResponse();

        try {
            Section section = sectionRepository.findByCode(code)
                    .orElseThrow(() -> new RuntimeException("Section not found with code " + code));
            sectionRepository.delete(section);

            apiResponse.setData(toDTO(section));
            apiResponse.setCode(0);
            apiResponse.setMessage("Section deleted");
        } catch (Exception e) {
            log.error("[CHECK DELETE] section not found with code {}", code);
            apiResponse.setCode(1);
            apiResponse.setMessage("Delete section failed");
        }

        return apiResponse;
    }

    private SectionDTO toDTO(Section section) {
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.code = section.getCode();
        sectionDTO.title = section.getTitle();
        sectionDTO.type = section.getSectionType().getName();
        sectionDTO.children = section.getSectionItems().stream().map(item -> {
            SectionItemDTO sectionItemDTO = new SectionItemDTO();
            sectionItemDTO.order = item.getOrder();
            sectionItemDTO.title = item.getTitle();
            sectionItemDTO.description = item.getDescription();
            sectionItemDTO.imageBase64 = item.getImage().getImageBase64();
            return sectionItemDTO;
        }).collect(Collectors.toList());
        return sectionDTO;
    }

    private Section fromDto(SectionDTO dto) {
        Section section = new Section();
        section.setCode(dto.code);
        section.setTitle(dto.title);

        SectionType sectionType = sectionTypeRepository.findByName(dto.type)
                .orElseThrow(() -> new RuntimeException("SectionType not found: " + dto.type));
        section.setSectionType(sectionType);

//        Convert SectionItemDTOs to SectionItems
        List<SectionItem> sectionItems = dto.children.stream().map(childDto -> {
            SectionItem SectionItem = new SectionItem();
            SectionItem.setOrder(childDto.order);
            SectionItem.setTitle(childDto.title);
            SectionItem.setDescription(childDto.description);

            Image image = new Image();
            image.setImageBase64(childDto.imageBase64);
            image = imageRepository.save(image);
            SectionItem.setImage(image);

//            Assign reverse relationship
            SectionItem.setSection(section);
            return SectionItem;
        }).collect(Collectors.toList());

        section.setSectionItems(sectionItems);
        return section;
    }

}
