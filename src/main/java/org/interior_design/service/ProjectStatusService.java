package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 5:17 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.IProjectStatusService;
import org.interior_design.dto.APIResponse;
import org.interior_design.model.ProjectStatus;
import org.interior_design.repository.ProjectStatusRepositoryJPA;
import org.interior_design.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectStatusService implements IProjectStatusService {
    private final ProjectStatusRepositoryJPA projectStatusRepositoryJPA;

    @Override
    public APIResponse getByID(Integer ID) {
        APIResponse apiResponse = new APIResponse();
        try {
            ProjectStatus projectStatus = projectStatusRepositoryJPA.findById(ID)
                    .orElseThrow(() -> new RuntimeException("ProjectStatus not found with ID: " + ID));
            apiResponse.setData(projectStatus);
            apiResponse.setCode(0);
            apiResponse.setMessage("ProjectStatus found");
        } catch (Exception e) {
            log.error("[CHECK GET BY ID] Error getting ProjectStatus: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error getting ProjectStatus");
        }
        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        APIResponse apiResponse = new APIResponse();
        try {
            Page<ProjectStatus> projectStatuses = projectStatusRepositoryJPA.findAll(pageable);
            apiResponse.setData(projectStatuses);
            apiResponse.setCode(0);
            apiResponse.setMessage("ProjectStatuses found");
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error getting ProjectStatuses: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error getting ProjectStatuses");
        }
        return apiResponse;
    }

    @Override
    public APIResponse create(ProjectStatus projectStatus) {
        APIResponse apiResponse = new APIResponse();
        try {
            projectStatus = projectStatusRepositoryJPA.save(projectStatus);
            apiResponse.setData(projectStatus);
            apiResponse.setCode(0);
            apiResponse.setMessage("ProjectStatus created");
        } catch (Exception e) {
            log.error("[CHECK CREATE] Error creating ProjectStatus: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error creating ProjectStatus");
        }
        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, ProjectStatus projectStatus) {
        APIResponse apiResponse = new APIResponse();
        try {
            ProjectStatus existingStatus = projectStatusRepositoryJPA.findById(ID)
                    .orElseThrow(() -> new RuntimeException("ProjectStatus not found with ID: " + ID));

            existingStatus.setName(projectStatus.getName());
            existingStatus.setRemarkEn(projectStatus.getRemarkEn());
            existingStatus.setRemark(projectStatus.getRemark());

            existingStatus = projectStatusRepositoryJPA.save(existingStatus);
            apiResponse.setData(existingStatus);
            apiResponse.setCode(0);
            apiResponse.setMessage("ProjectStatus updated");
        } catch (Exception e) {
            log.error("[CHECK UPDATE] Error updating ProjectStatus: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error updating ProjectStatus");
        }
        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse = new APIResponse();
        try {
            if (!projectStatusRepositoryJPA.existsById(ID)) {
                throw new RuntimeException("ProjectStatus not found with ID: " + ID);
            }
            projectStatusRepositoryJPA.deleteById(ID);
            apiResponse.setCode(0);
            apiResponse.setMessage("ProjectStatus deleted");
        } catch (Exception e) {
            log.error("[CHECK DELETE] Error deleting ProjectStatus: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error deleting ProjectStatus");
        }
        return apiResponse;
    }
}