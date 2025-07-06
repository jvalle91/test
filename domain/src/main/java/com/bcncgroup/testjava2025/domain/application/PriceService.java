package com.bcncgroup.testjava2025.domain.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcncgroup.testjava2025.domain.contant.MessageConstant;
import com.bcncgroup.testjava2025.domain.exception.LogicException;
import com.bcncgroup.testjava2025.domain.model.Price;
import com.bcncgroup.testjava2025.domain.ports.in.FindPriceUseCase;
import com.bcncgroup.testjava2025.domain.ports.in.query.FindPriceQuery;
import com.bcncgroup.testjava2025.domain.ports.out.PriceRepository;

import jakarta.transaction.Transactional;

/**
 * Service class that implements price use cases.
 * This class contains the business logic for price operations.
 */
@Service
@Transactional // Not persisting entities but ensures consistent view
public class PriceService implements FindPriceUseCase {
	
	private final PriceRepository priceRepository;
	
	@Autowired
	public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

	/**
     * Finds prices by date, product and brand, applying business rules.
     * This method searches for valid prices and applies priority rules.
     * 
     * @param query the search criteria
     * @return a list of prices ordered by priority
     */
	@Override
	public List<Price> findByDateProductAndBrand(FindPriceQuery query) {
		try {
			return priceRepository.findByDateProductAndBrand(query.applicationDate(), query.productId(), query.brandId());
		} catch (Exception ex) {
			throw new LogicException(MessageConstant.FIND_PRICES_ERROR, 404);
		}
	}
}
