package com.bcncgroup.testjava2025.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bcncgroup.testjava2025.domain.exception.LogicException;
import com.bcncgroup.testjava2025.domain.model.User;
import com.bcncgroup.testjava2025.infrastructure.adapter.JpaUserRepositoryAdapter;
import com.bcncgroup.testjava2025.infrastructure.entity.UserEntity;
import com.bcncgroup.testjava2025.infrastructure.mapper.UserMapper;
import com.bcncgroup.testjava2025.infrastructure.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("JpaUserRepositoryAdapter Tests")
class JpaUserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository jpaRepository;
    
    @Mock
    private UserMapper mapper;
    
    @InjectMocks
    private JpaUserRepositoryAdapter adapter;
    
    private String testUsername;
    private UserEntity userEntity;
    private User userDomain;
    
    @BeforeEach
    void setUp() {
        testUsername = "testuser";
        
        userEntity = new UserEntity();
        userEntity.setUsername(testUsername);
        userEntity.setPassword("encoded_password");
        
        userDomain = new User(testUsername, "encoded_password");
    }
    
    @Test
    @DisplayName("Should find user by username successfully")
    void findByUsername_OK_UserFound() {
        when(jpaRepository.findById(testUsername)).thenReturn(Optional.of(userEntity));
        when(mapper.toDomain(userEntity)).thenReturn(userDomain);
        
        User result = adapter.findByUsername(testUsername);
        
        assertNotNull(result);
        assertEquals(testUsername, result.username());
        assertEquals("encoded_password", result.password());
        
        verify(jpaRepository).findById(testUsername);
        verify(mapper).toDomain(userEntity);
    }
    
    @Test
    @DisplayName("Should throw LogicException when user not found")
    void findByUsername_KO_UserNotFound() {
        when(jpaRepository.findById(testUsername)).thenReturn(Optional.empty());
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByUsername(testUsername));
        
        assertEquals("user.not.found", exception.getMessage());
        assertEquals(404, exception.getCode());
        
        verify(jpaRepository).findById(testUsername);
        verify(mapper, never()).toDomain(any());
    }
    
    @Test
    @DisplayName("Should throw LogicException when repository throws exception")
    void findByUsername_KO_RepositoryException() {
        when(jpaRepository.findById(testUsername)).thenThrow(new RuntimeException("Database connection error"));
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByUsername(testUsername));
        
        assertEquals("general.error", exception.getMessage());
        assertEquals(500, exception.getCode());
        
        verify(jpaRepository).findById(testUsername);
        verify(mapper, never()).toDomain(any());
    }
    
    @Test
    @DisplayName("Should handle mapper exception")
    void findByUsername_KO_MapperException() {
        when(jpaRepository.findById(testUsername)).thenReturn(Optional.of(userEntity));
        when(mapper.toDomain(userEntity)).thenThrow(new RuntimeException("Mapping error"));
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByUsername(testUsername));
        
        assertEquals("general.error", exception.getMessage());
        assertEquals(500, exception.getCode());
        
        verify(jpaRepository).findById(testUsername);
        verify(mapper).toDomain(userEntity);
    }
    
    @Test
    @DisplayName("Should handle null username")
    void findByUsername_KO_NullUsername() {
        when(jpaRepository.findById(null)).thenThrow(new IllegalArgumentException("Username cannot be null"));
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByUsername(null));
        
        assertEquals("general.error", exception.getMessage());
        assertEquals(500, exception.getCode());
    }
    
    @Test
    @DisplayName("Should handle empty username")
    void findByUsername_KO_EmptyUsername() {
        String emptyUsername = "";
        when(jpaRepository.findById(emptyUsername)).thenReturn(Optional.empty());
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByUsername(emptyUsername));
        
        assertEquals("user.not.found", exception.getMessage());
        assertEquals(404, exception.getCode());
    }
}
