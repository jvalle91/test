package com.bcncgroup.testjava2025.domain.ports.in.query;

import java.time.LocalDateTime;

/**
 * Query object for finding prices by specific criteria.
 * This record contains the search parameters for price lookup.
 */
public record FindPriceQuery(
		LocalDateTime applicationDate,
		
	    Long productId,
	    
	    Long brandId) {

}
