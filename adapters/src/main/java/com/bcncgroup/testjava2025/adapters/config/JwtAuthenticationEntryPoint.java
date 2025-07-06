package com.bcncgroup.testjava2025.adapters.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT authentication entry point for handling unauthorized access.
 * This class handles authentication failures and sends error responses.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
     * Handles authentication failures by sending an unauthorized response.
     * This method is called when authentication fails.
     * 
     * @param request the HTTP request
     * @param response the HTTP response  
     * @param authException the authentication exception that occurred
     * @throws IOException if there is an error writing the response
     */
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
