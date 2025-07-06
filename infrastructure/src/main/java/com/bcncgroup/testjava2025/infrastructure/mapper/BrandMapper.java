package com.bcncgroup.testjava2025.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.bcncgroup.testjava2025.domain.model.Brand;
import com.bcncgroup.testjava2025.infrastructure.entity.BrandEntity;

/**
 * Mapper interface for converting between BrandEntity and Brand domain objects.
 * This interface uses MapStruct to generate mapping code automatically.
 */
@Mapper(componentModel = "spring")
public interface BrandMapper {
	
	/**
     * Converts a BrandEntity to a Brand domain object.
     * 
     * @param entity the brand entity from database
     * @return the brand domain object
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    Brand toDomain(BrandEntity entity);
    
    /**
     * Converts a Brand domain object to a BrandEntity.
     * 
     * @param domain the brand domain object
     * @return the brand entity for database
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    BrandEntity toEntity(Brand domain);

}
