package com.bcncgroup.testjava2025.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;

import com.bcncgroup.testjava2025.adapters.TestApplication;
import com.bcncgroup.testjava2025.infrastructure.conf.PersistenceConf;
import com.bcncgroup.testjava2025.infrastructure.entity.UserEntity;
import com.bcncgroup.testjava2025.infrastructure.repository.UserJpaRepository;

@DataJpaTest
@ContextConfiguration(classes = { TestApplication.class, PersistenceConf.class })
@DisplayName("UserJpaRepository Integration Tests")
class UserJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserJpaRepository userRepository;
    
    private UserEntity testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUsername("testuser");
        testUser.setPassword("encoded_password_hash");
        entityManager.persistAndFlush(testUser);
        entityManager.clear();
    }
    
    @Test
    @DisplayName("Should find user by existing username")
    void findById_OK_UserFound() {
        String username = "testuser";
        
        Optional<UserEntity> result = userRepository.findById(username);
        
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        assertEquals("encoded_password_hash", result.get().getPassword());
    }
    
    @Test
    @DisplayName("Should return empty when user not found")
    void findById_OK_UserNotFound() {
        String username = "nonexistentuser";
        
        Optional<UserEntity> result = userRepository.findById(username);
        
        assertFalse(result.isPresent());
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Should handle null username gracefully")
    void findById_KO_NullUsername() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.findById(null));
    }
    
    @Test
    @DisplayName("Should handle empty string username")
    void findById_OK_EmptyUsername() {
        String emptyUsername = "";
        
        Optional<UserEntity> result = userRepository.findById(emptyUsername);
        
        assertFalse(result.isPresent());
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Should save user successfully")
    void save_OK_UserSaved() {
        UserEntity newUser = new UserEntity();
        newUser.setUsername("newuser");
        newUser.setPassword("new_encoded_password");
        
        UserEntity savedUser = userRepository.save(newUser);
        
        assertNotNull(savedUser);
        assertEquals("newuser", savedUser.getUsername());
        assertEquals("new_encoded_password", savedUser.getPassword());
        
        Optional<UserEntity> foundUser = userRepository.findById("newuser");
        assertTrue(foundUser.isPresent());
        assertEquals("newuser", foundUser.get().getUsername());
    }
    
    @Test
    @DisplayName("Should update existing user")
    void save_OK_UserUpdated() {
        testUser.setPassword("updated_password");
        
        UserEntity updatedUser = userRepository.save(testUser);
        
        assertNotNull(updatedUser);
        assertEquals("testuser", updatedUser.getUsername());
        assertEquals("updated_password", updatedUser.getPassword());
        
        Optional<UserEntity> foundUser = userRepository.findById("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("updated_password", foundUser.get().getPassword());
    }
    
    @Test
    @DisplayName("Should count users correctly")
    void count_OK_CountUsers() {
        long initialCount = userRepository.count();
        
        UserEntity secondUser = new UserEntity();
        secondUser.setUsername("seconduser");
        secondUser.setPassword("second_password");
        userRepository.save(secondUser);
        
        long finalCount = userRepository.count();
        assertEquals(initialCount + 1, finalCount);
    }
    
    @Test
    @DisplayName("Should delete user successfully")
    void delete_OK_UserDeleted() {
        String username = testUser.getUsername();
        long initialCount = userRepository.count();
        
        assertTrue(userRepository.findById(username).isPresent());
        
        userRepository.delete(testUser);
        
        assertFalse(userRepository.findById(username).isPresent());
        assertEquals(initialCount - 1, userRepository.count());
    }
}