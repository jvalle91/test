package com.bcncgroup.testjava2025.adapters.dto;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Error response DTO for validation errors.
 * This record contains detailed validation error information.
 */
public record ValidationErrorResponseDTO(
		
		@Schema(description = "validations errors map ('field' : 'cause')", exampleClasses = Map.class)
		List<Map<String, String>> errors) {

}
