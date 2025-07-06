package com.bcncgroup.testjava2025.domain.exception;

/**
 * Custom exception class for logic layer errors.
 * This exception is thrown when there are problems in the business logic.
 */
public class LogicException extends RuntimeException {

	private static final long serialVersionUID = -8925175979816564862L;
	
	private final int code;

	public LogicException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
