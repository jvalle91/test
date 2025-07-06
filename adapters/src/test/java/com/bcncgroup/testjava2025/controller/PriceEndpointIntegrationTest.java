package com.bcncgroup.testjava2025.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bcncgroup.testjava2025.adapters.controller.PriceController;
import com.bcncgroup.testjava2025.adapters.dto.in.PriceFindInDTO;
import com.bcncgroup.testjava2025.adapters.dto.out.PriceFindOutDTO;
import com.bcncgroup.testjava2025.adapters.mapper.PriceFindInDTOMapper;
import com.bcncgroup.testjava2025.adapters.mapper.PriceFindOutDTOMapper;
import com.bcncgroup.testjava2025.domain.model.Brand;
import com.bcncgroup.testjava2025.domain.model.Price;
import com.bcncgroup.testjava2025.domain.ports.in.FindPriceUseCase;
import com.bcncgroup.testjava2025.domain.ports.in.query.FindPriceQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Integration test for Price endpoint covering business scenarios
 * Uses standalone MockMvc
 */
class PriceEndpointIntegrationTest {

    private MockMvc mockMvc;
    
    @Mock
    private FindPriceUseCase findPriceUseCase;
    
    @Mock
    private PriceFindInDTOMapper priceFindInDTOMapper;
    
    @Mock
    private PriceFindOutDTOMapper priceFindOutDTOMapper;
    
    private PriceController priceController;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceController = new PriceController(findPriceUseCase, priceFindInDTOMapper, priceFindOutDTOMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }
    
    @ParameterizedTest
    @MethodSource("providePriceTestScenarios")
    void shouldReturnCorrectPriceForRequestedDateProductAndBrand(
            String testName,
            LocalDateTime requestDate,
            Long productId,
            Long brandId,
            Integer expectedPriceList,
            Double expectedPrice,
            String expectedCurrency) throws Exception {
        
        PriceFindInDTO requestDTO = new PriceFindInDTO(requestDate, productId, brandId);
        
        Brand mockBrand = new Brand(brandId, "ZARA", "Marca de moda española");
        Price mockPrice = new Price(1L, mockBrand, requestDate.minusHours(1), requestDate.plusHours(1), 
                                  expectedPriceList, productId, 1, new BigDecimal(expectedPrice.toString()), expectedCurrency);
        
        PriceFindOutDTO.BrandFindOutDTO brandDTO = new PriceFindOutDTO.BrandFindOutDTO(brandId, "ZARA", "Marca de moda española");
        PriceFindOutDTO responseDTO = new PriceFindOutDTO(productId, brandDTO, expectedPriceList, 
                                                        requestDate.minusHours(1), requestDate.plusHours(1),
                                                        new BigDecimal(expectedPrice.toString()), expectedCurrency);
        
        when(findPriceUseCase.findByDateProductAndBrand(any(FindPriceQuery.class))) .thenReturn(List.of(mockPrice));
        when(priceFindOutDTOMapper.toDto(any(Price.class))) .thenReturn(responseDTO);
        
        mockMvc.perform(post("/price/findByDateProductIdentifierBrand")
                .content(mapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(productId))
                .andExpect(jsonPath("$[0].brand.id").value(brandId))
                .andExpect(jsonPath("$[0].brand.name").value("ZARA"))
                .andExpect(jsonPath("$[0].priceList").value(expectedPriceList))
                .andExpect(jsonPath("$[0].price").value(expectedPrice))
                .andExpect(jsonPath("$[0].currency").value(expectedCurrency));
    }
    
    private static Stream<Arguments> providePriceTestScenarios() {
        return Stream.of(
            Arguments.of(
                "Test 1: Request at 10:00 on 14th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 14, 10, 0, 0),
                35455L,
                1L,
                1,
                35.50,
                "EUR"
            ),
            Arguments.of(
                "Test 2: Request at 16:00 on 14th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 14, 16, 0, 0),
                35455L,
                1L,
                2,
                25.45,
                "EUR"
            ),
            Arguments.of(
                "Test 3: Request at 21:00 on 14th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 14, 21, 0, 0),
                35455L,
                1L,
                1,
                35.50,
                "EUR"
            ),
            Arguments.of(
                "Test 4: Request at 10:00 on 15th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 15, 10, 0, 0),
                35455L,
                1L,
                3,
                30.50,
                "EUR"
            ),
            Arguments.of(
                "Test 5: Request at 21:00 on 16th for product 35455 brand 1 (ZARA)",
                LocalDateTime.of(2020, 6, 16, 21, 0, 0),
                35455L,
                1L,
                4,
                38.95,
                "EUR"
            )
        );
    }
}