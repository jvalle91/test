package com.bcncgroup.testjava2025.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bcncgroup.testjava2025.domain.model.Price;
import com.bcncgroup.testjava2025.infrastructure.entity.PriceEntity;

/**
 * Mapper interface for converting between PriceEntity and Price domain objects.
 * This interface uses MapStruct to generate mapping code automatically.
 */
@Mapper(componentModel = "spring", uses = { BrandMapper.class })
public interface PriceMapper {

    /**
     * Converts a PriceEntity to a Price domain object.
     * 
     * @param entity the price entity from database
     * @return the price domain object
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "brand", source = "brand")
    Price toDomain(PriceEntity entity);
    
    /**
     * Converts a Price domain object to a PriceEntity.
     * 
     * @param domain the price domain object
     * @return the price entity for database
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "brand", source = "brand")
    PriceEntity toEntity(Price domain);
}

