package com.bcncgroup.testjava2025.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bcncgroup.testjava2025.infrastructure.entity.PriceEntity;

/**
 * Repository interface for accessing price data in the database.
 * This interface extends JpaRepository and provides custom query methods.
 */
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

	/**
     * Finds prices that match the given criteria.
     * This method searches for prices by application date, product ID and brand ID.
     * 
     * @param applicationDate the date when the price should be valid
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @return a list of price entities that match the criteria
     */
	@Query("""
	        SELECT p
	        FROM PriceEntity p
	        WHERE p.productId = :productId
	          AND p.brand.id = :brandId
	          AND :applicationDate BETWEEN p.startDate AND p.endDate
	        ORDER BY p.priority
	    """)
	    List<PriceEntity> findByDateProductIdentifierBrand(
	    		// we can use the start date to application date
	        @Param("applicationDate") LocalDateTime applicationDate,
	        @Param("productId") Long productId,
	        @Param("brandId") Long brandId
	    );
	
}
