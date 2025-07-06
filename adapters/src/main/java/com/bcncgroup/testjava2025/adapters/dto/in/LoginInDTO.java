package com.bcncgroup.testjava2025.adapters.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Input DTO for user login requests.
 * This record contains the credentials needed for authentication.
 */
public record LoginInDTO(
		
		@Schema(description = "username", example = "test")
		@NotBlank(message = "username.empty")
		String username,
		
		@Schema(description = "password", example = "test")
		@NotBlank(message = "password.empty")
        String password
		) {

}
