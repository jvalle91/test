package com.bcncgroup.testjava2025.domain.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.bcncgroup.testjava2025.domain.contant.MessageConstant;

/**
 * Password manager class that handles password encoding and verification.
 * This class implements Spring Security's PasswordEncoder interface.
 */
public class PasswordMamanger implements PasswordEncoder {

	private static final String ALGORITHM = "SHA-256";
	private static final String SEPARATOR = "$";
	private static final int SALT_LENGTH = 16;

	/**
     * Encodes a raw password using SHA-256 algorithm with salt.
     * This method generates a random salt and creates a secure hash.
     * 
     * @param rawPassword the plain text password to encode
     * @return the encoded password in format: salt$hashedPassword
     */
	public String encode(CharSequence rawPassword) {
        // Generate random salt
        String salt = generateSalt();
        
        String hashedPassword = hashPassword(rawPassword.toString(), salt);
        
        // format: salt$hashedPassword
        return salt + SEPARATOR + hashedPassword;
    }

	/**
     * Checks if a raw password matches an encoded password.
     * This method extracts the salt and compares the hashes.
     * 
     * @param rawPassword the plain text password to check
     * @param encodedPassword the encoded password to compare against
     * @return true if the passwords match, false otherwise
     */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            // Separate salt and hash
            String[] parts = encodedPassword.split("\\" + SEPARATOR);
            if (parts.length != 2) {
                return false;
            }
            
            String salt = parts[0];
            String storedHash = parts[1];
            
            String hashedInput = hashPassword(rawPassword.toString(), salt);
            
            // compare hashes
            return storedHash.equals(hashedInput);
        } catch (Exception e) {
            return false;
        }
    }

	/**
     * Generates a random salt for password hashing.
     * This method creates a secure random salt using SecureRandom.
     * 
     * @return a Base64 encoded salt string
     */
	private String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_LENGTH];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	/**
     * Creates a hash of the password using the provided salt.
     * This method uses SHA-256 algorithm to hash the password.
     * 
     * @param password the plain text password
     * @param salt the salt to use for hashing
     * @return a Base64 encoded hash string
     * @throws RuntimeException if the hashing algorithm is not available
     */
	private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            
            md.update(salt.getBytes());
            byte[] hashedBytes = md.digest(password.getBytes());
            
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(MessageConstant.PASSWORD_ENCRIPT_ERROR, e);
        }
    }
}
