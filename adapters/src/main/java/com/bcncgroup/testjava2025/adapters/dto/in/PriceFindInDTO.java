package com.bcncgroup.testjava2025.adapters.dto.in;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

/**
 * Input DTO for price search requests.
 * This record contains the criteria for finding prices.
 */
public record PriceFindInDTO(
		
		@Schema(description = "application date", example = "2020-06-14 18:00:00")
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@NotNull(message = "price.startDate.null")
	    @PastOrPresent(message = "price.startDate.startdate.future")
		LocalDateTime startDate,
		
		@Schema(description = "product id", example = "35455")
		@NotNull(message = "price.productId.null")
	    @Positive(message = "price.productId.negative")
		Long productId,
		
		@Schema(description = "brand id", example = "1")
		@NotNull(message = "price.brandId.null")
	    @Positive(message = "price.brandId.negative")
		Long brandId) {

}
