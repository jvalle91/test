package com.bcncgroup.testjava2025.domain.ports.in;

import java.util.List;

import com.bcncgroup.testjava2025.domain.model.Price;
import com.bcncgroup.testjava2025.domain.ports.in.query.FindPriceQuery;

/**
 * Use case for finding prices by date, product and brand.
 * This interface defines the contract for price search operations.
 */
public interface FindPriceUseCase {
	
	/**
     * Finds prices that match the given search criteria.
     * 
     * @param query the search criteria containing date, product ID and brand ID
     * @return a list of prices that match the criteria
     */
    List<Price> findByDateProductAndBrand(FindPriceQuery query);

}
