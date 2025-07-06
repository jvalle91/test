package com.bcncgroup.testjava2025.adapters.dto.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Output DTO for price search responses.
 * This record contains price information with brand details.
 */
public record PriceFindOutDTO(
    
    @Schema(description = "product id", example = "35455")
    Long productId,
    
    @Schema(description = "brand of product")
    BrandFindOutDTO brand,
    
    @Schema(description = "price list id", example = "1")
    Integer priceList,
    
    @Schema(description = "start date of price application", example = "2020-06-14 15:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startDate,
    
    @Schema(description = "end date of price application", example = "2020-06-14 18:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endDate,
    
    @Schema(description = "final price to apply", example = "25.45")
    BigDecimal price,
    
    @Schema(description = "currency", example = "EUR")
    String currency
) {

	/**
     * Nested DTO for brand information in price responses.
     * This record contains brand details.
     */
    public record BrandFindOutDTO(
        @Schema(description = "brand id", example = "1")
        Long id,
        
        @Schema(description = "brand name", example = "Zara")
        String name,
        
        @Schema(description = "description of brand", example = "Marca de moda española")
        String description
    ) {
    }
}