package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/06/04
 * Time: 5:17 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.IImageAttributeService;
import org.interior_design.dto.APIResponse;
import org.interior_design.model.ImageAttribute;
import org.interior_design.repository.ImageAttributeRepositoryJPA;
import org.interior_design.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class ImageAttributeService implements IImageAttributeService {
    private final ImageAttributeRepositoryJPA imageAttributeRepositoryJPA;

    @Override
    public APIResponse getByID(Integer ID) {
        APIResponse apiResponse = new APIResponse();
        try {
            ImageAttribute imageAttribute = imageAttributeRepositoryJPA.findById(ID)
                    .orElseThrow(() -> new RuntimeException("ImageAttribute not found with ID: " + ID));
            apiResponse.setData(imageAttribute);
            apiResponse.setCode(0);
            apiResponse.setMessage("ImageAttribute found");
        } catch (Exception e) {
            log.error("[CHECK GET BY ID] Error getting ImageAttribute: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error getting ImageAttribute");
        }
        return apiResponse;
    }

    @Override
    public APIResponse getByAttrAndType(Integer attr, String type) {
        APIResponse apiResponse = new APIResponse();
        try {
            List<ImageAttribute> imageAttributes = imageAttributeRepositoryJPA
                    .findByAttrIdAndType(attr, Integer.parseInt(type));
            apiResponse.setData(imageAttributes);
            apiResponse.setCode(0);
            apiResponse.setMessage("ImageAttributes found");
        } catch (Exception e) {
            log.error("[CHECK GET BY ATTR AND TYPE] Error getting ImageAttributes: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error getting ImageAttributes");
        }
        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        APIResponse apiResponse = new APIResponse();
        try {
            Page<ImageAttribute> imageAttributes = imageAttributeRepositoryJPA.findAll(pageable);
            apiResponse.setData(imageAttributes);
            apiResponse.setCode(0);
            apiResponse.setMessage("ImageAttributes found");
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error getting ImageAttributes: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error getting ImageAttributes");
        }
        return apiResponse;
    }

    @Override
    public APIResponse create(ImageAttribute imageAttribute) {
        APIResponse apiResponse = new APIResponse();
        try {
            imageAttribute = imageAttributeRepositoryJPA.save(imageAttribute);
            apiResponse.setData(imageAttribute);
            apiResponse.setCode(0);
            apiResponse.setMessage("ImageAttribute created");
        } catch (Exception e) {
            log.error("[CHECK CREATE] Error creating ImageAttribute: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error creating ImageAttribute");
        }
        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, ImageAttribute imageAttribute) {
        APIResponse apiResponse = new APIResponse();
        try {
            ImageAttribute existingAttribute = imageAttributeRepositoryJPA.findById(ID)
                    .orElseThrow(() -> new RuntimeException("ImageAttribute not found with ID: " + ID));

            existingAttribute.setImage(imageAttribute.getImage());
            existingAttribute.setAttrId(imageAttribute.getAttrId());
            existingAttribute.setType(imageAttribute.getType());
            existingAttribute.setIsPrimary(imageAttribute.getIsPrimary());

            existingAttribute = imageAttributeRepositoryJPA.save(existingAttribute);
            apiResponse.setData(existingAttribute);
            apiResponse.setCode(0);
            apiResponse.setMessage("ImageAttribute updated");
        } catch (Exception e) {
            log.error("[CHECK UPDATE]Error updating ImageAttribute: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error updating ImageAttribute");
        }
        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse = new APIResponse();
        try {
            if (!imageAttributeRepositoryJPA.existsById(ID)) {
                throw new RuntimeException("ImageAttribute not found with ID: " + ID);
            }
            imageAttributeRepositoryJPA.deleteById(ID);
            apiResponse.setCode(0);
            apiResponse.setMessage("ImageAttribute deleted");
        } catch (Exception e) {
            log.error("[CHECK DELETE] Error deleting ImageAttribute: {}", Utils.printStackTrace(e));
            apiResponse.setCode(1);
            apiResponse.setMessage("Error deleting ImageAttribute");
        }
        return apiResponse;
    }
}