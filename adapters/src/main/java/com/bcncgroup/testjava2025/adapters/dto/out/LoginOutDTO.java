package com.bcncgroup.testjava2025.adapters.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Output DTO for login responses.
 * This record contains the authentication token and user information.
 */
public record LoginOutDTO(
		@Schema(description = "jwt authentication token", example = "stringbase64")
		String jwt,
		
		@Schema(description = "username", example = "test")
		String username) {

}
