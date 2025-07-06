package com.bcncgroup.testjava2025.adapters.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bcncgroup.testjava2025.adapters.dto.GeneralErrorResponseDTO;
import com.bcncgroup.testjava2025.adapters.dto.ValidationErrorResponseDTO;
import com.bcncgroup.testjava2025.domain.contant.MessageConstant;
import com.bcncgroup.testjava2025.domain.exception.LogicException;

/**
 * Global exception handler for REST controllers.
 * This class handles exceptions.
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);
	
	/**
     * Handles generic exceptions that are not specifically handled.
     * 
     * @param ex the exception that occurred
     * @return error response with internal server error status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorResponseDTO> handleGenericException(Exception ex) {
    	LOG.error(MessageConstant.GENERAL_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GeneralErrorResponseDTO(MessageConstant.GENERAL_ERROR, 500));
    }
	
    /**
     * Handles validation exceptions from request data validation.
     * 
     * @param ex the validation exception
     * @return error response with validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> validationException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        
        LOG.warn(MessageConstant.INVALID_DATA + " {}", errors.toString());
        
        return ResponseEntity.status(400).body(new ValidationErrorResponseDTO(errors));
    }

    /**
     * Handles logic layer exceptions.
     * This method only knows about LogicException and uses its error code.
     * 
     * @param ex the logic exception
     * @return error response with appropriate status and message
     */
    @ExceptionHandler(LogicException.class)
    public ResponseEntity<GeneralErrorResponseDTO> handleLogicException(LogicException ex) {
    	LOG.error(MessageConstant.LOGIC_ERROR, ex);
        return ResponseEntity.status(ex.getCode()).body(new GeneralErrorResponseDTO(ex.getMessage(), ex.getCode()));
    }
}
