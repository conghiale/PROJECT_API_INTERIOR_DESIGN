package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 4:51 PM
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.IProjectService;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.ProjectDTO;
import org.interior_design.dto.ProjectStatusDTO;
import org.interior_design.model.Image;
import org.interior_design.model.ImageAttribute;
import org.interior_design.model.Project;
import org.interior_design.model.ProjectStatus;
import org.interior_design.repository.ImageAttributeRepositoryJPA;
import org.interior_design.repository.ImageRepositoryJPA;
import org.interior_design.repository.ProjectRepositoryJPA;
import org.interior_design.repository.ProjectStatusRepositoryJPA;
import org.interior_design.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectService implements IProjectService {
    private final ProjectRepositoryJPA projectRepositoryJPA;
    private final ProjectStatusRepositoryJPA projectStatusRepositoryJPA;
    private final ImageRepositoryJPA imageRepositoryJPA;
    private final ImageAttributeRepositoryJPA imageAttributeRepositoryJPA;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public APIResponse getBySlug(String slug) {
        APIResponse apiResponse = new APIResponse();
        try {
            Project project = projectRepositoryJPA.findBySlug(slug)
                    .orElseThrow(() -> new RuntimeException("Project not found with slug: " + slug));
            apiResponse.setData(projectToDTO(project));
            apiResponse.setCode(0);
            apiResponse.setMessage("Project found");
        } catch (Exception e) {
            log.error("[CHECK GET BY SLUG] Error getting project: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error getting project");
        }
        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        log.debug("[CHECK GET ALL] getAll CALLED!!!");
        APIResponse apiResponse = new APIResponse();
        try {
            Page<Project> projects = projectRepositoryJPA.findAll(pageable);
            Page<ProjectDTO> projectDTOs = projects.map(this::projectToDTO);
            apiResponse.setData(projectDTOs);
            apiResponse.setCode(0);
            apiResponse.setMessage("Projects found");
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error getting projects: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error getting projects");
        }
        return apiResponse;
    }

    @Override
    public APIResponse create(ProjectDTO projectDTO) {
        APIResponse apiResponse = new APIResponse();
        try {
            Project project = projectFromDTO(projectDTO);
            project = projectRepositoryJPA.save(project);

//            Handle images
            saveProjectImages(project, projectDTO);

            apiResponse.setData(projectToDTO(project));
            apiResponse.setCode(0);
            apiResponse.setMessage("Project created successfully");
        } catch (Exception e) {
            log.error("[CHECK CREATE] Error creating project: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error creating project");
        }
        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, ProjectDTO projectDTO) {
        APIResponse apiResponse = new APIResponse();
        try {
            Project existingProject = projectRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Project not found with ID: " + ID));

            updateProjectFromDTO(existingProject, projectDTO);
            existingProject = projectRepositoryJPA.save(existingProject);

//            Update images
//            imageAttributeRepositoryJPA.deleteByAttrIdAndType(existingProject.getID(), 1);
            updateProjectImages(existingProject, projectDTO);

            apiResponse.setData(projectToDTO(existingProject));
            apiResponse.setCode(0);
            apiResponse.setMessage("Project updated successfully");
        } catch (Exception e) {
            log.error("Error updating project: {}", e.getMessage());
            apiResponse.setCode(1);
            apiResponse.setMessage("Error updating project");
        }

        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse = new APIResponse();
        try {
            Project project = projectRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Project not found with ID: " + ID));

//            Delete associated images
            List<ImageAttribute> imageAttributes = imageAttributeRepositoryJPA
                    .findByAttrIdAndType(project.getID(), 1);
            for (ImageAttribute attr : imageAttributes) {
                imageRepositoryJPA.delete(attr.getImage());
            }

            projectRepositoryJPA.delete(project);
            apiResponse.setCode(0);
            apiResponse.setMessage("Project deleted successfully");
        } catch (Exception e) {
            log.error("[CHECK DELETE] Error deleting project: {}", e.getMessage());
            apiResponse.setCode(1);
            apiResponse.setMessage("Error deleting project");
        }
        return apiResponse;
    }

    private ProjectDTO projectToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setID(project.getID());
        dto.setSlug(project.getSlug());
        dto.setTitle(project.getTitle());
        dto.setInvestor(project.getInvestor());
        dto.setAcreage(project.getAcreage());
        dto.setDescription(project.getDescription());
        dto.setDesigner(project.getDesigner());
        dto.setLocation(project.getLocation());
        dto.setFactory(project.getFactory());
        dto.setHotline(project.getHotline());
        dto.setEmail(project.getEmail());
        dto.setWebsite(project.getWebsite());
        dto.setProjectStatusDTO(projectStatusToDTO(project.getProjectStatus()));

//        Get project images
        List<ImageAttribute> imageAttributes = imageAttributeRepositoryJPA
                .findByAttrIdAndType(project.getID(), 1);

        dto.setImages(imageAttributes.stream()
                .map(attr -> attr.getImage().getImageBase64())
                .collect(Collectors.toList()));

//        Find the index of the primary image
        OptionalInt primaryIndexOpt = IntStream.range(0, imageAttributes.size())
                .filter(i -> Boolean.TRUE.equals(imageAttributes.get(i).getIsPrimary()))
                .findFirst();

        primaryIndexOpt.ifPresent(dto::setIndexPrimaryImage);

        return dto;
    }

    private Project projectFromDTO(ProjectDTO dto) {
        Project project = new Project();
        updateProjectFromDTO(project, dto);
        return project;
    }

    private void updateProjectFromDTO(Project project, ProjectDTO dto) {
        project.setSlug(dto.getSlug());
        project.setTitle(dto.getTitle());
        project.setInvestor(dto.getInvestor());
        project.setAcreage(dto.getAcreage());
        project.setDescription(dto.getDescription());
        project.setDesigner(dto.getDesigner());
        project.setLocation(dto.getLocation());
        project.setFactory(dto.getFactory());
        project.setHotline(dto.getHotline());
        project.setEmail(dto.getEmail());
        project.setWebsite(dto.getWebsite());

        ProjectStatus status = projectStatusRepositoryJPA.findByID(dto.getProjectStatusDTO().getID())
                .orElseThrow(() -> new RuntimeException("Project status not found: " + dto.getProjectStatusDTO().getRemarkEn()));
        project.setProjectStatus(status);
    }

    private ProjectStatusDTO projectStatusToDTO(ProjectStatus projectStatus) {
        ProjectStatusDTO projectStatusDTO = new ProjectStatusDTO();
        projectStatusDTO.setID(projectStatus.getID());
        projectStatusDTO.setName(projectStatus.getName());
        projectStatusDTO.setRemarkEn(projectStatus.getRemarkEn());
        projectStatusDTO.setRemark(projectStatus.getRemark());
        return projectStatusDTO;
    }

    private ProjectStatus projectStatusFromDTO(ProjectStatusDTO projectStatusDTO) {
        ProjectStatus projectStatus = new ProjectStatus();
        updateProjectStatusFromDTO(projectStatus, projectStatusDTO);
        return projectStatus;
    }

    private void updateProjectStatusFromDTO(ProjectStatus projectStatus, ProjectStatusDTO dto) {
        projectStatus.setName(dto.getName());
        projectStatus.setRemark(dto.getRemark());
        projectStatus.setRemarkEn(dto.getRemarkEn());
    }

    private void saveProjectImages(Project project, ProjectDTO dto) {
        if (dto.getImages() != null) {
            for (int i = 0; i < dto.getImages().size(); i++) {
                String imageBase64 = dto.getImages().get(i);
                Image image = new Image();
                image.setImageBase64(imageBase64);
                image = imageRepositoryJPA.save(image);

                ImageAttribute imageAttribute = new ImageAttribute();
                imageAttribute.setImage(image);
                imageAttribute.setAttrId(project.getID());
                imageAttribute.setType(1);
                imageAttribute.setIsPrimary(dto.getIndexPrimaryImage() == i ? Boolean.TRUE : Boolean.FALSE);
                imageAttributeRepositoryJPA.save(imageAttribute);
            }
        }
    }

    private void updateProjectImages(Project project, ProjectDTO dto) {
//        Get existing image attributes
        List<ImageAttribute> existingAttributes = imageAttributeRepositoryJPA
                .findByAttrIdAndType(project.getID(), 1);

        if (dto.getImages() != null) {
//            Update or create images as needed
            for (int i = 0; i < dto.getImages().size(); i++) {
                String imageBase64 = dto.getImages().get(i);
                boolean isPrimary = dto.getIndexPrimaryImage() == i;

                if (i < existingAttributes.size()) {
//                    Update existing image
                    ImageAttribute existingAttr = existingAttributes.get(i);
                    Image existingImage = existingAttr.getImage();
                    existingImage.setImageBase64(imageBase64);
                    imageRepositoryJPA.save(existingImage);

//                    Update attribute
                    existingAttr.setIsPrimary(isPrimary);
                    imageAttributeRepositoryJPA.save(existingAttr);
                } else {
//                    Create new image and attribute
                    Image newImage = new Image();
                    newImage.setImageBase64(imageBase64);
                    newImage = imageRepositoryJPA.save(newImage);

                    ImageAttribute newAttr = new ImageAttribute();
                    newAttr.setImage(newImage);
                    newAttr.setAttrId(project.getID());
                    newAttr.setType(1);
                    newAttr.setIsPrimary(isPrimary);
                    imageAttributeRepositoryJPA.save(newAttr);
                }
            }

//            Remove redundant images
            if (existingAttributes.size() > dto.getImages().size()) {
                for (int i = dto.getImages().size(); i < existingAttributes.size(); i++) {
                    ImageAttribute redundantAttr = existingAttributes.get(i);
                    Image redundantImage = redundantAttr.getImage();
                    imageAttributeRepositoryJPA.delete(redundantAttr);
                    imageRepositoryJPA.delete(redundantImage);
                }
            }
        } else {
//            If no images in DTO, remove all existing images
            for (ImageAttribute attr : existingAttributes) {
                imageAttributeRepositoryJPA.delete(attr);
                imageRepositoryJPA.delete(attr.getImage());
            }
        }
    }
}
