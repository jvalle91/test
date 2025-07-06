package com.bcncgroup.testjava2025.adapters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcncgroup.testjava2025.adapters.config.JwtUtil;
import com.bcncgroup.testjava2025.adapters.dto.GeneralErrorResponseDTO;
import com.bcncgroup.testjava2025.adapters.dto.in.LoginInDTO;
import com.bcncgroup.testjava2025.adapters.dto.out.LoginOutDTO;
import com.bcncgroup.testjava2025.domain.contant.MessageConstant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST controller for authentication operations.
 * This controller handles user login using use cases.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "authentications/authorizations operations")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	/**
     * Authenticates a user and returns a JWT token.
     * This method uses use cases for authentication.
     * 
     * @param arg the login credentials
     * @return response with JWT token or error message
     * @throws Exception if there is an authentication error
     */
	@PostMapping("/login")
    @Operation(summary = "start new session", description = "authenticate an user and return a session token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login succesfull"),
            @ApiResponse(responseCode = "401", description = "invalid credentials"),
            @ApiResponse(responseCode = "400", description = "invalid data"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
	public ResponseEntity<?> login(@Valid @RequestBody LoginInDTO arg) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(arg.username(), arg.password()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(arg.username());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            LoginOutDTO dto = new LoginOutDTO(jwt, userDetails.getUsername());
            
            return ResponseEntity.ok(dto);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new GeneralErrorResponseDTO(MessageConstant.INVALID_CREDENTIALS, 401));
        }
    }
}
