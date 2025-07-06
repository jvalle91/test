package com.bcncgroup.testjava2025.domain.ports.out;

import java.time.LocalDateTime;
import java.util.List;

import com.bcncgroup.testjava2025.domain.model.Price;

/**
 * Port for price data access operations.
 * This interface defines the contract for price persistence operations.
 */
public interface PriceRepository {
	
	/**
     * Finds prices that are valid for the given date, product and brand.
     * 
     * @param applicationDate the date when the price should be valid
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @return a list of prices that match the criteria
     */
    List<Price> findByDateProductAndBrand(LocalDateTime applicationDate, Long productId, Long brandId);

}
