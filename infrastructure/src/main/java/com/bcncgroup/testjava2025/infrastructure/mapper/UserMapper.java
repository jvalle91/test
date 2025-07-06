package com.bcncgroup.testjava2025.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.bcncgroup.testjava2025.domain.model.User;
import com.bcncgroup.testjava2025.infrastructure.entity.UserEntity;

/**
 * Mapper interface for converting between UserEntity and User domain objects.
 * This interface uses MapStruct to generate mapping code automatically.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
	
	/**
     * Converts a User domain object to a UserEntity.
     * 
     * @param domain the user domain object
     * @return the user entity for database
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    UserEntity toEntity(User domain);
    
    /**
     * Converts a UserEntity to a User domain object.
     * 
     * @param entity the user entity from database
     * @return the user domain object
     */
    @BeanMapping(builder = @Builder(disableBuilder = true))
    User toDomain(UserEntity entity);

}
