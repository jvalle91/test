package com.bcncgroup.testjava2025.infrastructure.adapter;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bcncgroup.testjava2025.domain.contant.MessageConstant;
import com.bcncgroup.testjava2025.domain.exception.LogicException;
import com.bcncgroup.testjava2025.domain.model.User;
import com.bcncgroup.testjava2025.domain.ports.out.UserRepository;
import com.bcncgroup.testjava2025.infrastructure.mapper.UserMapper;
import com.bcncgroup.testjava2025.infrastructure.repository.UserJpaRepository;

/**
 * JPA adapter that implements the UserRepository port.
 * This class connects the domain logic with JPA persistence for users.
 */
@Repository
public class JpaUserRepositoryAdapter implements UserRepository {
	
	static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
	
	private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    public JpaUserRepositoryAdapter(UserJpaRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    /**
     * Finds a user by their username.
     * This method searches in the database and converts entity to domain object.
     * 
     * @param username the username to search for
     * @return an optional containing the domain user object if found
     */
    @Override
    public User findByUsername(String username) {
        try {
            return jpaRepository.findById(username).map(mapper::toDomain).orElseThrow();
        } catch(NoSuchElementException ex) {
        	LOG.warn(MessageConstant.USER_NOT_FOUND, ex);
        	throw new LogicException(MessageConstant.USER_NOT_FOUND, 404);
        } catch (Exception ex) {
        	LOG.error(MessageConstant.GENERAL_ERROR, ex);
            throw new LogicException(MessageConstant.GENERAL_ERROR, 500);
        }
    }

}
