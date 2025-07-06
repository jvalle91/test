package com.bcncgroup.testjava2025.infrastructure.adapter;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bcncgroup.testjava2025.domain.contant.MessageConstant;
import com.bcncgroup.testjava2025.domain.exception.LogicException;
import com.bcncgroup.testjava2025.domain.model.Price;
import com.bcncgroup.testjava2025.domain.ports.out.PriceRepository;
import com.bcncgroup.testjava2025.infrastructure.entity.PriceEntity;
import com.bcncgroup.testjava2025.infrastructure.mapper.PriceMapper;
import com.bcncgroup.testjava2025.infrastructure.repository.PriceJpaRepository;

/**
 * JPA adapter that implements the PriceRepository port.
 * This class connects the domain logic with JPA persistence.
 */
@Repository
public class JpaPriceRepositoryAdapter implements PriceRepository {
	
	static final Logger LOG = LoggerFactory.getLogger(PriceRepository.class);
	
	private final PriceJpaRepository jpaRepository;
    private final PriceMapper mapper;
    
    public JpaPriceRepositoryAdapter(PriceJpaRepository jpaRepository, PriceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    /**
     * Finds prices that are valid for the given date, product and brand.
     * This method delegates to JPA repository and converts entities to domain objects.
     * 
     * @param applicationDate the date when the price should be valid
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @return a list of domain price objects
     */
	@Override
	public List<Price> findByDateProductAndBrand(LocalDateTime applicationDate, Long productId, Long brandId) {
		try {
			List<PriceEntity> entities = jpaRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);

			return entities.stream().map(mapper::toDomain).toList();
		} catch (Exception ex) {
			LOG.error(MessageConstant.FIND_PRICES_ERROR, ex);
			throw new LogicException(MessageConstant.FIND_PRICES_ERROR, 500);
		}
	}
}
