package com.bcncgroup.testjava2025.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bcncgroup.testjava2025.domain.exception.LogicException;
import com.bcncgroup.testjava2025.domain.model.Brand;
import com.bcncgroup.testjava2025.domain.model.Price;
import com.bcncgroup.testjava2025.infrastructure.adapter.JpaPriceRepositoryAdapter;
import com.bcncgroup.testjava2025.infrastructure.entity.BrandEntity;
import com.bcncgroup.testjava2025.infrastructure.entity.PriceEntity;
import com.bcncgroup.testjava2025.infrastructure.mapper.PriceMapper;
import com.bcncgroup.testjava2025.infrastructure.repository.PriceJpaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("JpaPriceRepositoryAdapter Tests")
class JpaPriceRepositoryAdapterTest {

    @Mock
    private PriceJpaRepository jpaRepository;
    
    @Mock
    private PriceMapper mapper;
    
    @InjectMocks
    private JpaPriceRepositoryAdapter adapter;
    
    private LocalDateTime testDate;
    private Long productId;
    private Long brandId;
    private PriceEntity priceEntity;
    private Price priceDomain;
    
    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        productId = 35455L;
        brandId = 1L;
        
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        brandEntity.setName("ZARA");
        brandEntity.setDescription("Marca de moda española");
        
        priceEntity = new PriceEntity();
        priceEntity.setId(1L);
        priceEntity.setBrand(brandEntity);
        priceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        priceEntity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        priceEntity.setPriceList(1);
        priceEntity.setProductId(productId);
        priceEntity.setPriority(0);
        priceEntity.setPrice(new BigDecimal("35.50"));
        priceEntity.setCurrency("EUR");
        
        Brand brand = new Brand(brandId, "ZARA", "Marca de moda española");
        priceDomain = new Price(1L, brand, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 1, productId, 0, new BigDecimal("35.50"), "EUR");
    }
    
    @Test
    @DisplayName("Should find prices by date, product and brand successfully")
    void findByDateProductAndBrand_OK_SinglePrice() {
        List<PriceEntity> entities = Arrays.asList(priceEntity);
        List<Price> expectedDomainPrices = Arrays.asList(priceDomain);
        
        when(jpaRepository.findByDateProductIdentifierBrand(testDate, productId, brandId)).thenReturn(entities);
        when(mapper.toDomain(priceEntity)).thenReturn(priceDomain);
        
        List<Price> result = adapter.findByDateProductAndBrand(testDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDomainPrices.get(0), result.get(0));
        
        verify(jpaRepository).findByDateProductIdentifierBrand(testDate, productId, brandId);
        verify(mapper).toDomain(priceEntity);
    }
    
    @Test
    @DisplayName("Should return empty list when no prices found")
    void findByDateProductAndBrand_OK_EmptyResult() {
        when(jpaRepository.findByDateProductIdentifierBrand(testDate, productId, brandId)).thenReturn(Collections.emptyList());
        
        List<Price> result = adapter.findByDateProductAndBrand(testDate, productId, brandId);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(jpaRepository).findByDateProductIdentifierBrand(testDate, productId, brandId);
        verify(mapper, never()).toDomain(any());
    }
    
    @Test
    @DisplayName("Should handle multiple prices with correct mapping")
    void findByDateProductAndBrand_OK_MultiplePrices() {
        PriceEntity secondPriceEntity = new PriceEntity();
        secondPriceEntity.setId(2L);
        secondPriceEntity.setBrand(priceEntity.getBrand());
        secondPriceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0));
        secondPriceEntity.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30));
        secondPriceEntity.setPriceList(2);
        secondPriceEntity.setProductId(productId);
        secondPriceEntity.setPriority(1);
        secondPriceEntity.setPrice(new BigDecimal("25.45"));
        secondPriceEntity.setCurrency("EUR");
        
        Price secondPriceDomain = new Price(2L, new Brand(brandId, "ZARA", "Marca de moda española"), LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 2, productId, 1, new BigDecimal("25.45"), "EUR");
        
        List<PriceEntity> entities = Arrays.asList(priceEntity, secondPriceEntity);
        
        when(jpaRepository.findByDateProductIdentifierBrand(testDate, productId, brandId)).thenReturn(entities);
        when(mapper.toDomain(priceEntity)).thenReturn(priceDomain);
        when(mapper.toDomain(secondPriceEntity)).thenReturn(secondPriceDomain);
        
        List<Price> result = adapter.findByDateProductAndBrand(testDate, productId, brandId);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(priceDomain, result.get(0));
        assertEquals(secondPriceDomain, result.get(1));
        
        verify(jpaRepository).findByDateProductIdentifierBrand(testDate, productId, brandId);
        verify(mapper, times(2)).toDomain(any(PriceEntity.class));
    }
    
    @Test
    @DisplayName("Should throw LogicException when repository throws exception")
    void findByDateProductAndBrand_KO_RepositoryException() {
        when(jpaRepository.findByDateProductIdentifierBrand(testDate, productId, brandId)).thenThrow(new RuntimeException("Database connection error"));
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByDateProductAndBrand(testDate, productId, brandId));
        
        assertEquals("find.prices.error", exception.getMessage());
        assertEquals(500, exception.getCode());
        
        verify(jpaRepository).findByDateProductIdentifierBrand(testDate, productId, brandId);
        verify(mapper, never()).toDomain(any());
    }
    
    @Test
    @DisplayName("Should throw LogicException when mapper throws exception")
    void findByDateProductAndBrand_KO_MapperException() {
        List<PriceEntity> entities = Arrays.asList(priceEntity);
        
        when(jpaRepository.findByDateProductIdentifierBrand(testDate, productId, brandId)).thenReturn(entities);
        when(mapper.toDomain(priceEntity)).thenThrow(new RuntimeException("Mapping error"));
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByDateProductAndBrand(testDate, productId, brandId));
        
        assertEquals("find.prices.error", exception.getMessage());
        assertEquals(500, exception.getCode());
        
        verify(jpaRepository).findByDateProductIdentifierBrand(testDate, productId, brandId);
        verify(mapper).toDomain(priceEntity);
    }
    
    @Test
    @DisplayName("Should handle null parameters gracefully")
    void findByDateProductAndBrand_KO_NullParameters() {
        when(jpaRepository.findByDateProductIdentifierBrand(null, null, null)).thenThrow(new IllegalArgumentException("Invalid parameters"));
        
        LogicException exception = assertThrows(LogicException.class, () -> adapter.findByDateProductAndBrand(null, null, null));
        
        assertEquals("find.prices.error", exception.getMessage());
        assertEquals(500, exception.getCode());
    }
}