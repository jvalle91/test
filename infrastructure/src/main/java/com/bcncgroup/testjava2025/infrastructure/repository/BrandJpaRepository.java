package com.bcncgroup.testjava2025.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcncgroup.testjava2025.infrastructure.entity.BrandEntity;

/**
 * Repository interface for accessing brand data in the database.
 * This interface extends JpaRepository to provide basic CRUD operations.
 */
public interface BrandJpaRepository extends JpaRepository<BrandEntity, Long> {

}
