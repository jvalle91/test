package com.bcncgroup.testjava2025.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class that represents a user in the database.
 * This class maps to the USERS table and stores user information.
 */
@Table(name = "USERS")
@Entity
public class UserEntity {
	
	@Id
	@Column(name = "USERNAME", nullable = false)
	String username;
	
	@Column(name = "PASSWORD", nullable = false)
	String password;
	
	// without roles, It's not necessary to separate on UserAccount-UserData entities

	/**
     * Gets the username of the user.
     * 
     * @return the username
     */
	public String getUsername() {
		return username;
	}

	/**
     * Sets the username of the user.
     * 
     * @param username the username to set
     */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * Gets the password of the user.
     * 
     * @return the password
     */
	public String getPassword() {
		return password;
	}

	/**
     * Sets the password of the user.
     * 
     * @param password the password to set
     */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
