package com.bcncgroup.testjava2025.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Error response DTO for general errors.
 * This record contains error information for API responses.
 */
public record GeneralErrorResponseDTO(
		@Schema(description = "error description code", example = "general.error")
		String cause,
		
		@Schema(description = "internal error code", example = "500")
		int code
		) {

}
