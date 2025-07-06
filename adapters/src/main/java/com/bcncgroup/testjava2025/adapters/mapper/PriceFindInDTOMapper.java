package com.bcncgroup.testjava2025.adapters.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.bcncgroup.testjava2025.adapters.dto.in.PriceFindInDTO;
import com.bcncgroup.testjava2025.domain.model.Brand;
import com.bcncgroup.testjava2025.domain.model.Price;

/**
 * Mapper interface for converting PriceFindInDTO to Price domain objects.
 * This interface uses MapStruct to generate mapping code automatically.
 */
@Mapper(componentModel = "spring")
public interface PriceFindInDTOMapper {
	
	/**
     * Converts a PriceFindInDTO to a Price domain object.
     * This method maps input DTO to domain object for business logic.
     * 
     * @param arg the price input DTO to convert
     * @return the price domain object
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "brandIdToBrand")
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "priceList", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "currency", ignore = true)
    Price toDomain(PriceFindInDTO arg);

    /**
     * Maps a brand ID to a Brand domain object.
     * This method creates a brand domain object with only the ID.
     * 
     * @param brandId the brand identifier
     * @return the brand domain object
     */
    @Named("brandIdToBrand")
    default Brand mapBrandIdToBrand(Long brandId) {
        return new Brand(brandId, null, null);
    }

    /**
     * Converts a Price domain object to a PriceFindInDTO.
     * This method maps domain object back to input DTO.
     * 
     * @param domain the price domain object to convert
     * @return the price input DTO
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(source = "brand.id", target = "brandId")
    PriceFindInDTO toDto(Price domain);

}
