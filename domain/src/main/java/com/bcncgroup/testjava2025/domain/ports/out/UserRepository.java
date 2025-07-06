package com.bcncgroup.testjava2025.domain.ports.out;

import com.bcncgroup.testjava2025.domain.model.User;

/**
 * JPA repository interface for accessing price entities.
 * This interface provides pure database operations for PriceEntity.
 */
public interface UserRepository {
	
	/**
     * Finds a user by their username.
     * This method searches for a user in the persistence layer using the username as identifier.
     * 
     * @param username the username to search for, must not be null or empty
     * @return the user domain object if found
     * @throws com.bcncgroup.testjava2025.logic.exception.LogicException if user is not found (404) or database error occurs (500)
     */
	User findByUsername(String username);
}
