package com.bcncgroup.testjava2025.adapters.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bcncgroup.testjava2025.infrastructure.adapter.JpaUserRepositoryAdapter;

/**
 * Implementation of Spring Security's UserDetailsService.
 * This class loads user details for authentication using the JPA adapter directly.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final JpaUserRepositoryAdapter userRepositoryAdapter;
	
	@Autowired
    public UserDetailsServiceImpl(JpaUserRepositoryAdapter userRepositoryAdapter) {
        this.userRepositoryAdapter = userRepositoryAdapter;
    }

	/**
     * Loads user details by username for authentication.
     * This method retrieves user data using the JPA adapter.
     * 
     * @param username the username to load details for
     * @return the user details for authentication
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            com.bcncgroup.testjava2025.domain.model.User user = userRepositoryAdapter.findByUsername(username);
            
            return new User(user.username(), user.password(), List.of());
        } catch (Exception ex) {
            throw new UsernameNotFoundException("user.not.found");
        }
    }
}
