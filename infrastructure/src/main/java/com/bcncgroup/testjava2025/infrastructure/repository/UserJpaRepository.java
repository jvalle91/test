package com.bcncgroup.testjava2025.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcncgroup.testjava2025.infrastructure.entity.UserEntity;

/**
 * Repository interface for accessing user data in the database.
 * This interface extends JpaRepository to provide basic CRUD operations.
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

}
