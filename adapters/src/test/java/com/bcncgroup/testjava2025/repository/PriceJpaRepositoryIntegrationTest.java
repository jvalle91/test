package com.bcncgroup.testjava2025.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.bcncgroup.testjava2025.adapters.TestApplication;
import com.bcncgroup.testjava2025.infrastructure.conf.PersistenceConf;
import com.bcncgroup.testjava2025.infrastructure.entity.PriceEntity;
import com.bcncgroup.testjava2025.infrastructure.repository.PriceJpaRepository;

/**
 * Validates 5 specific business scenarios
 * Uses H2 in-memory database with pre-loaded data to verify correct price selection logic
 */
@DataJpaTest
@ContextConfiguration(classes = { TestApplication.class, PersistenceConf.class })
class PriceJpaRepositoryIntegrationTest {

    @Autowired
    private PriceJpaRepository priceRepository;
    
    @ParameterizedTest
    @MethodSource("provideRequiredTestScenarios")
    void shouldReturnCorrectPriceForAllRequiredBusinessScenarios(
            String testDescription,
            LocalDateTime requestDate,
            Long productId,
            Long brandId,
            Double expectedPrimaryPrice,
            Integer expectedPrimaryPriceList,
            String expectedCurrency) {
        
        // When - Execute the query that the endpoint would execute
        List<PriceEntity> result = priceRepository.findByDateProductIdentifierBrand(requestDate, productId, brandId);
        
        // Then - Verify results match business requirements
        assertNotNull(result, "Result should not be null for: " + testDescription);
        
        // Should return at least one price (the one with highest priority)
        assertEquals(true, !result.isEmpty(), "Should find at least one price for: " + testDescription);
        
        // The first result should be the highest priority price (priority 0 beats priority 1)
        PriceEntity primaryPrice = result.get(0);
        assertEquals(expectedPrimaryPrice, primaryPrice.getPrice().doubleValue(), 0.01, 
                   "Primary price should match for: " + testDescription);
        assertEquals(expectedPrimaryPriceList, primaryPrice.getPriceList(), 
                   "Primary price list should match for: " + testDescription);
        assertEquals(expectedCurrency, primaryPrice.getCurrency(), 
                   "Currency should match for: " + testDescription);
        
        // Verify core business data
        assertEquals(productId, primaryPrice.getProductId(), "Product ID should match");
        assertEquals(brandId, primaryPrice.getBrand().getId(), "Brand ID should match");
        assertEquals("ZARA", primaryPrice.getBrand().getName(), "Brand name should be ZARA");
        
        // Verify the date falls within the price validity period
        assertEquals(true, !requestDate.isBefore(primaryPrice.getStartDate()), 
                   "Request date should be >= start date");
        assertEquals(true, !requestDate.isAfter(primaryPrice.getEndDate()), 
                   "Request date should be <= end date");
    }
    
    /**
     * Provides the exact 5 test scenarios specified in requirements
     */
    private static Stream<Arguments> provideRequiredTestScenarios() {
        return Stream.of(
            Arguments.of(
                "Test 1: Request at 10:00 on 14th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 14, 10, 0, 0),
                35455L,
                1L,
                35.50, // Only base price applies at this time
                1,     // Base price list
                "EUR"
            ),
            Arguments.of(
                "Test 2: Request at 16:00 on 14th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 14, 16, 0, 0),
                35455L,
                1L,
                35.50, // Base price wins (priority 0 beats priority 1)
                1,     // Base price list
                "EUR"
            ),
            Arguments.of(
                "Test 3: Request at 21:00 on 14th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 14, 21, 0, 0),
                35455L,
                1L,
                35.50, // Only base price applies (promotional ended at 18:30)
                1,     // Base price list
                "EUR"
            ),
            Arguments.of(
                "Test 4: Request at 10:00 on 15th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 15, 10, 0, 0),
                35455L,
                1L,
                35.50, // Base price wins (priority 0 beats priority 1)
                1,     // Base price list
                "EUR"
            ),
            Arguments.of(
                "Test 5: Request at 21:00 on 16th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 16, 21, 0, 0),
                35455L,
                1L,
                35.50, // Base price wins (priority 0 beats priority 1)
                1,     // Base price list
                "EUR"
            )
        );
    }
}