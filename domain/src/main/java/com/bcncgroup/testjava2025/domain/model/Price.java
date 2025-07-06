package com.bcncgroup.testjava2025.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Value object that represents price data.
 * This record holds price information for data transfer.
 */
public record Price(
	    Long id,
	    Brand brand,
	    LocalDateTime startDate,
	    LocalDateTime endDate,
	    Integer priceList,
	    Long productId,
	    Integer priority,
	    BigDecimal price,
	    String currency
		) {
}
