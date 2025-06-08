package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:19 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.IService.IQuoteService;
import org.interior_design.dto.APIResponse;
import org.interior_design.dto.QuoteDTO;
import org.interior_design.dto.QuoteItemDTO;
import org.interior_design.dto.TestimonialDTO;
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
public class QuoteService implements IQuoteService {
    private final QuoteRepositoryJPA quoteRepositoryJPA;
    private final QuoteTypeRepositoryJPA quoteTypeRepositoryJPA;
    private final QuoteItemRepositoryJPA quoteItemRepositoryJPA;

    @Override
    public APIResponse getByID(Integer ID) {
        APIResponse apiResponse;
        try {
            Quote quote = quoteRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Quote with id " + ID + " not found"));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Quote with id " + ID + " found")
                    .data(toDTO(quote))
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET BY ID] Error while getting quote by id {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Quote with id " + ID + " not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse getAll(Pageable pageable) {
        APIResponse apiResponse;
        try {
            Page<QuoteDTO> quoteDTOPage = quoteRepositoryJPA.findAll(pageable).map(this::toDTO);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Quote found")
                    .data(quoteDTOPage)
                    .build();
        } catch (Exception e) {
            log.error("[CHECK GET ALL] Error while getting all quotes. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Quote not found")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse create(QuoteDTO quoteDTO) {
        APIResponse apiResponse;
        try {
            quoteDTO = toDTO(quoteRepositoryJPA.save(saveFromDTO(quoteDTO)));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Quote created")
                    .data(quoteDTO)
                    .build();

        } catch (Exception e) {
            log.error("[CHECK CREATE] Error while add quote. Details: {}", Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Create quote failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse update(Integer ID, QuoteDTO quoteDTO) {
        APIResponse apiResponse;
        try {
            Quote quote = quoteRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Quote with ID " + ID + " not found"));

//            Use orphanRemoval = true in Quote model
            updateFromDTO_v2(quote, quoteDTO);
            quoteDTO = toDTO(quoteRepositoryJPA.save(quote));

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Quote updated")
                    .data(quoteDTO)
                    .build();

//            Note use orphanRemoval = true in Quote model
//            1. Transaction 1: Update Quote -> Commit
//            2. Transaction 2: Update QuoteItem -> Commit
            /*updateFromDTO(quote, quoteDTO);
            quoteRepositoryJPA.save(quote);
            updateQuoteItem(quote, quoteDTO);

            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Quote updated")
                    .data(toDTO(quote))
                    .build();*/

        } catch (Exception e) {
            log.error("[CHECK UPDATE] Error while update quote with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Updated quote failed")
                    .build();
        }

        return apiResponse;
    }

    @Override
    public APIResponse delete(Integer ID) {
        APIResponse apiResponse;
        try {
            Quote quote = quoteRepositoryJPA.findByID(ID)
                    .orElseThrow(() -> new RuntimeException("Quote with ID " + ID + " not found"));

            quoteRepositoryJPA.delete(quote);

//            Builder does not serialize from a deleted implementation. Use dto
            apiResponse = APIResponse.builder()
                    .code(0)
                    .message("Quote deleted")
                    .data(toDTO(quote))
                    .build();

        } catch (Exception e) {
            log.error("[CHECK DELETE] Error while delete quote with ID {}. Details: {}", ID, Utils.printStackTrace(e));
            apiResponse = APIResponse.builder()
                    .code(-1)
                    .message("Delete quote failed")
                    .build();
        }

        return apiResponse;
    }

    private QuoteDTO toDTO(Quote quote) {
        QuoteDTO dto = new QuoteDTO();
        dto.setID(quote.getID());
        dto.setTitle(quote.getTitle());
        dto.setSubtitle(quote.getSubtitle());
        dto.setQuoteTypeId(quote.getQuoteType().getID());
        dto.setQuoteTypeName(quote.getQuoteType().getName());

        List<QuoteItemDTO> itemDTOs = quote.getQuoteItems().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
        dto.setQuoteItems(itemDTOs);

        return dto;
    }

    private QuoteItemDTO toItemDTO(QuoteItem item) {
        QuoteItemDTO dto = new QuoteItemDTO();
        dto.setID(item.getID());
        dto.setContent(item.getContent());
        dto.setPrice(item.getPrice());
        return dto;
    }

    private Quote fromDTO(QuoteDTO dto) {
        Quote quote = new Quote();
        updateFromDTO(quote, dto);
        return quote;
    }

    private void updateFromDTO(Quote quote, QuoteDTO dto) {
        QuoteType quoteType = quoteTypeRepositoryJPA.findByID(dto.getQuoteTypeId())
                .orElseThrow(() -> new RuntimeException("QuoteType not found with ID: " + dto.getQuoteTypeId()));
        quote.setQuoteType(quoteType);
        quote.setTitle(dto.getTitle());
        quote.setSubtitle(dto.getSubtitle());
    }

    private void updateQuoteItem(Quote quote, QuoteDTO dto) {
        if (dto.getQuoteItems() != null) {
//            Get existing quote items
            List<QuoteItem> existingItems = quoteItemRepositoryJPA.findByQuoteID(quote.getID());

            for (int i = 0; i < dto.getQuoteItems().size(); i++) {
                QuoteItemDTO itemDTO = dto.getQuoteItems().get(i);

                if (i < existingItems.size()) {
//                    Update existing item
                    QuoteItem existingItem = existingItems.get(i);
                    existingItem.setContent(itemDTO.getContent());
                    existingItem.setPrice(itemDTO.getPrice());
                } else {
//                    Create new item
                    QuoteItem newItem = new QuoteItem();
                    newItem.setContent(itemDTO.getContent());
                    newItem.setPrice(itemDTO.getPrice());
                    newItem.setQuote(quote);
                    existingItems.add(newItem);
                }
            }

//            Remove redundant items
            for (int i = existingItems.size() - 1; i >= dto.getQuoteItems().size(); i--) {
                QuoteItem redundantItem = existingItems.get(i);
                quoteItemRepositoryJPA.delete(redundantItem);
                existingItems.remove(i);
            }
        } else {
//            If no items in DTO, remove all existing items
            quoteItemRepositoryJPA.deleteAll(quote.getQuoteItems());
        }
    }

//    Use orphanRemoval = true in Quote model
    private void updateFromDTO_v2(Quote quote, QuoteDTO dto) {
        QuoteType quoteType = quoteTypeRepositoryJPA.findByID(dto.getQuoteTypeId())
                .orElseThrow(() -> new RuntimeException("QuoteType not found with ID: " + dto.getQuoteTypeId()));
        quote.setQuoteType(quoteType);
        quote.setTitle(dto.getTitle());
        quote.setSubtitle(dto.getSubtitle());

        if (dto.getQuoteItems() != null) {
//            Get existing quote items
            List<QuoteItem> existingItems = quote.getQuoteItems();

//            Remove the leftovers
            int dtoSize = dto.getQuoteItems().size();
            if (existingItems.size() > dtoSize) {
//                Hibernate will delete itself thanks to orphanRemoval
                existingItems.subList(dtoSize, existingItems.size()).clear();
            }

//            Update or add new
            for (int i = 0; i < dtoSize; i++) {
                QuoteItemDTO itemDTO = dto.getQuoteItems().get(i);
                if (i < existingItems.size()) {
                    QuoteItem item = existingItems.get(i);
                    item.setContent(itemDTO.getContent());
                    item.setPrice(itemDTO.getPrice());
                } else {
                    QuoteItem newItem = new QuoteItem();
                    newItem.setContent(itemDTO.getContent());
                    newItem.setPrice(itemDTO.getPrice());
                    newItem.setQuote(quote);
                    existingItems.add(newItem); // Add to old managed list
                }
            }
        } else {
//            If no items in DTO, remove all existing items
            quote.getQuoteItems().clear();
        }
    }

    private Quote saveFromDTO(QuoteDTO dto) {
        Quote quote = new Quote();
        quote.setTitle(dto.getTitle());
        quote.setSubtitle(dto.getSubtitle());

        QuoteType quoteType = quoteTypeRepositoryJPA.findByID(dto.getQuoteTypeId())
                .orElseThrow(() -> new RuntimeException("QuoteType not found with ID: " + dto.getQuoteTypeId()));
        quote.setQuoteType(quoteType);

        if (dto.getQuoteItems() != null) {
            List<QuoteItem> items = dto.getQuoteItems().stream()
                    .map(itemDTO -> fromItemDTO(itemDTO, quote))
                    .collect(Collectors.toList());
            quote.setQuoteItems(items);
        }

        return quote;
    }

    private QuoteItem fromItemDTO(QuoteItemDTO dto, Quote quote) {
        QuoteItem item = new QuoteItem();
        item.setID(dto.getID());
        item.setContent(dto.getContent());
        item.setPrice(dto.getPrice());
        item.setQuote(quote);
        return item;
    }
}
