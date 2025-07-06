package com.bcncgroup.testjava2025.adapters.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.bcncgroup.testjava2025.adapters.dto.out.PriceFindOutDTO;
import com.bcncgroup.testjava2025.domain.model.Brand;
import com.bcncgroup.testjava2025.domain.model.Price;

/**
 * Mapper interface for converting Price domain objects to PriceFindOutDTO.
 * This interface uses MapStruct to generate mapping code automatically.
 */
@Mapper(componentModel = "spring")
public interface PriceFindOutDTOMapper {
    
	/**
     * Converts a Price domain object to a PriceFindOutDTO.
     * This method maps domain object to output DTO for API responses.
     * 
     * @param domain the price domain object to convert
     * @return the price output DTO
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    PriceFindOutDTO toDto(Price domain);

    /**
     * Maps a Brand domain object to a BrandFindOutDTO.
     * This method converts brand domain object to output DTO.
     * 
     * @param brand the brand domain object to convert
     * @return the brand output DTO
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    PriceFindOutDTO.BrandFindOutDTO mapBrand(Brand brand);
}