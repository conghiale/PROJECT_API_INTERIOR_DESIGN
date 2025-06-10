package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:19 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.IProjectInfoService;
import org.interior_design.IService.IQuoteService;
import org.interior_design.dto.*;
import org.interior_design.model.*;
import org.interior_design.repository.*;
import org.interior_design.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectInfoService implements IProjectInfoService {
    private final ProjectInfoRepositoryJPA projectInfoRepositoryJPA;
    private final ProjectInfoItemRepositoryJPA projectInfoItemRepositoryJPA;

    @Override
    public APIResponse getByID(Integer ID) {
        APIResponse apiResponse;
        try {
            ProjectInfo projectInfo = projectInfoRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Project info not found with ID: " + ID));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Project info found")
                    .data(toDTO(projectInfo))
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET BY ID] Error while getting project info by ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Project info not found")
                    .build();
        }
        return apiResponse;
    }

    @Override
    public APIResponse getAll() {
        APIResponse apiResponse;
        try {
            List<ProjectInfoDTO> projectInfoDTOs = projectInfoRepositoryJPA.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Project info found")
                    .data(projectInfoDTOs)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error while getting all project info. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Project info not found")
                    .build();
        }
        return apiResponse;
    }

    @Override
    public APIResponse create(ProjectInfoDTO projectInfoDTO) {
        APIResponse apiResponse;
        try {
            ProjectInfo projectInfo = fromDTO(projectInfoDTO);
            projectInfoDTO = toDTO(projectInfoRepositoryJPA.save(projectInfo));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Project info created")
                    .data(projectInfoDTO)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK CREATE] Error while creating project info. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Create project info failed")
                    .build();
        }
        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, ProjectInfoDTO projectInfoDTO) {
        APIResponse apiResponse;
        try {
            ProjectInfo projectInfo = projectInfoRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Project info not found with ID: " + ID));

            updateFromDTO(projectInfo, projectInfoDTO);
            projectInfoDTO = toDTO(projectInfoRepositoryJPA.save(projectInfo));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Project info updated")
                    .data(projectInfoDTO)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK UPDATE] Error while updating project info. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Update project info failed")
                    .build();
        }
        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse;
        try {
            ProjectInfo projectInfo = projectInfoRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Project info not found with ID: " + ID));

            projectInfoRepositoryJPA.delete(projectInfo);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Project info deleted")
                    .data(toDTO(projectInfo))
                    .build();
        } catch (Exception e) {
            log.error("[CHECK DELETE] Error while deleting project info. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Delete project info failed")
                    .build();
        }
        return apiResponse;
    }

    private ProjectInfoDTO toDTO(ProjectInfo projectInfo) {
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setID(projectInfo.getID());
        dto.setTypicalProjectTitle(projectInfo.getTypicalProjectTitle());
        dto.setTypicalProjectSubTitle(projectInfo.getTypicalProjectSubTitle());
        dto.setFeaturedProjectTitle(projectInfo.getFeaturedProjectTitle());
        dto.setFeaturedProjectSubTitle(projectInfo.getFeaturedProjectSubTitle());

        List<ProjectInfoItemDTO> itemDTOs = projectInfo.getProjectInfoItems().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
        dto.setProjectInfoItems(itemDTOs);

        return dto;
    }

    private ProjectInfoItemDTO toItemDTO(ProjectInfoItem item) {
        ProjectInfoItemDTO dto = new ProjectInfoItemDTO();
        dto.setID(item.getID());
        dto.setItemTitle(item.getItemTitle());
        dto.setItemNumber(item.getItemNumber());
        dto.setRemarkEn(item.getRemarkEn());
        dto.setRemark(item.getRemark());
        return dto;
    }

    private ProjectInfo fromDTO(ProjectInfoDTO dto) {
        ProjectInfo projectInfo = new ProjectInfo();
        updateFromDTO(projectInfo, dto);
        return projectInfo;
    }

//    Use orphanRemoval = true in model
    private void updateFromDTO(ProjectInfo projectInfo, ProjectInfoDTO dto) {
        projectInfo.setTypicalProjectTitle(dto.getTypicalProjectTitle());
        projectInfo.setTypicalProjectSubTitle(dto.getTypicalProjectSubTitle());
        projectInfo.setFeaturedProjectTitle(dto.getFeaturedProjectTitle());
        projectInfo.setFeaturedProjectSubTitle(dto.getFeaturedProjectSubTitle());

        if (dto.getProjectInfoItems() != null) {
//            Get existing Project Info items
            List<ProjectInfoItem> existingItems = projectInfo.getProjectInfoItems();
            if (existingItems == null) {
                existingItems = new ArrayList<>();
                projectInfo.setProjectInfoItems(existingItems);
            }

//            Remove the leftovers
            int dtoSize = dto.getProjectInfoItems().size();
            if (existingItems.size() > dtoSize) {
//                Hibernate will delete itself thanks to orphanRemoval
                existingItems.subList(dtoSize, existingItems.size()).clear();
            }

//            Update or add new
            for (int i = 0; i < dtoSize; i++) {
                ProjectInfoItemDTO itemDTO = dto.getProjectInfoItems().get(i);
                ProjectInfoItem item;

                if (i < existingItems.size()) {
//                    Update existing item
                    item = existingItems.get(i);
                    item.setItemTitle(itemDTO.getItemTitle());
                    item.setItemNumber(itemDTO.getItemNumber());
                    item.setRemarkEn(itemDTO.getRemarkEn());
                    item.setRemark(itemDTO.getRemark());
                } else {
//                    Create new item
                    item = new ProjectInfoItem();
                    item.setItemTitle(itemDTO.getItemTitle());
                    item.setItemNumber(itemDTO.getItemNumber());
                    item.setRemarkEn(itemDTO.getRemarkEn());
                    item.setRemark(itemDTO.getRemark());
                    item.setProjectInfo(projectInfo);
                    existingItems.add(item);
                }
            }

        } else {
//            If no items in DTO, remove all existing items
            projectInfo.getProjectInfoItems().clear();
        }
    }
}
