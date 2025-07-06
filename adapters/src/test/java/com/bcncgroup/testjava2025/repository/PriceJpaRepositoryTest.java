package com.bcncgroup.testjava2025.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.bcncgroup.testjava2025.adapters.TestApplication;
import com.bcncgroup.testjava2025.infrastructure.conf.PersistenceConf;
import com.bcncgroup.testjava2025.infrastructure.entity.PriceEntity;
import com.bcncgroup.testjava2025.infrastructure.repository.PriceJpaRepository;

@DataJpaTest
@ContextConfiguration(classes = { TestApplication.class, PersistenceConf.class })
@DisplayName("PriceJpaRepository Integration Tests")
class PriceJpaRepositoryTest {

    @Autowired
    private PriceJpaRepository priceRepository;
    
    @Test
    @DisplayName("Test 1: Should find price at 10:00 on 14th for product 35455 and brand 1")
    void findByDateProductIdentifierBrand_OK_Test1() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(35.50, result.get(0).getPrice().doubleValue());
        assertEquals(0, result.get(0).getPriority());
        assertEquals(1, result.get(0).getPriceList());
    }
    
    @Test
    @DisplayName("Test 2: Should find 2 prices at 16:00 on 14th ordered by priority")
    void findByDateProductIdentifierBrand_OK_Test2_OrderByPriority() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertEquals(0, result.get(0).getPriority(), "First price should have priority 0");
        assertEquals(35.50, result.get(0).getPrice().doubleValue());
        
        assertEquals(1, result.get(1).getPriority(), "Second price should have priority 1");
        assertEquals(25.45, result.get(1).getPrice().doubleValue());
    }
    
    @Test
    @DisplayName("Test 3: Should find price at 21:00 on 14th (only base price)")
    void findByDateProductIdentifierBrand_OK_Test3() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(35.50, result.get(0).getPrice().doubleValue());
        assertEquals(0, result.get(0).getPriority());
    }
    
    @Test
    @DisplayName("Test 4: Should find 2 prices at 10:00 on 15th ordered by priority")
    void findByDateProductIdentifierBrand_OK_Test4_OrderByPriority() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertEquals(0, result.get(0).getPriority(), "First price should have priority 0");
        assertEquals(35.50, result.get(0).getPrice().doubleValue());
        
        assertEquals(1, result.get(1).getPriority(), "Second price should have priority 1");
        assertEquals(30.50, result.get(1).getPrice().doubleValue());
    }
    
    @Test
    @DisplayName("Test 5: Should find 2 prices at 21:00 on 16th ordered by priority")
    void findByDateProductIdentifierBrand_OK_Test5_OrderByPriority() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertEquals(0, result.get(0).getPriority(), "First price should have priority 0");
        assertEquals(35.50, result.get(0).getPrice().doubleValue());
        
        assertEquals(1, result.get(1).getPriority(), "Second price should have priority 1");
        assertEquals(38.95, result.get(1).getPrice().doubleValue());
    }
    
    @Test
    @DisplayName("Should return empty list when no prices found for different product")
    void findByDateProductIdentifierBrand_OK_DifferentProduct() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 99999L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Should return empty list when no prices found for different brand")
    void findByDateProductIdentifierBrand_OK_DifferentBrand() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 35455L;
        Long brandId = 999L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Should return empty list when date is outside price ranges")
    void findByDateProductIdentifierBrand_OK_OutsideRange() {
        LocalDateTime applicationDate = LocalDateTime.of(2019, 6, 14, 10, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Should handle exact boundary dates correctly")
    void findByDateProductIdentifierBrand_OK_BoundaryDates() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;
        
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(applicationDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        
        boolean foundBasePrice = result.stream().anyMatch(p -> p.getPrice().doubleValue() == 35.50);
        boolean foundSpecialPrice = result.stream().anyMatch(p -> p.getPrice().doubleValue() == 25.45);
        
        assertTrue(foundBasePrice, "Should find base price 35.50");
        assertTrue(foundSpecialPrice, "Should find special price 25.45");
    }
}
