package com.bcncgroup.testjava2025.adapters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcncgroup.testjava2025.adapters.dto.in.PriceFindInDTO;
import com.bcncgroup.testjava2025.adapters.dto.out.PriceFindOutDTO;
import com.bcncgroup.testjava2025.adapters.mapper.PriceFindInDTOMapper;
import com.bcncgroup.testjava2025.adapters.mapper.PriceFindOutDTOMapper;
import com.bcncgroup.testjava2025.domain.model.Price;
import com.bcncgroup.testjava2025.domain.ports.in.FindPriceUseCase;
import com.bcncgroup.testjava2025.domain.ports.in.query.FindPriceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * REST controller for price operations.
 * This controller handles price search and filtering requests.
 */
@RestController
@RequestMapping("/price")
public class PriceController {
	
	private final FindPriceUseCase findPriceUseCase;
	
	private final PriceFindOutDTOMapper priceFindOutDTOMapper;

	@Autowired
	public PriceController(FindPriceUseCase findPriceUseCase, PriceFindInDTOMapper priceDTOMapper, PriceFindOutDTOMapper priceFindOutDTOMapper) {
		super();
		this.findPriceUseCase = findPriceUseCase;
		this.priceFindOutDTOMapper = priceFindOutDTOMapper;
	}
	
	/**
     * Finds prices by date, product identifier and brand.
     * This method uses for price search.
     * 
     * @param arg the search criteria containing date, product ID and brand ID
     * @return list of prices that match the criteria
     */
	@PostMapping("/findByDateProductIdentifierBrand")
    @Operation(summary = "find prices", description = "filter product prices according to the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "filter succesfull"),
            @ApiResponse(responseCode = "400", description = "invalid data"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<List<PriceFindOutDTO>> findByDateProductIdentifierBrand(@Valid @RequestBody PriceFindInDTO arg) {
        FindPriceQuery query = new FindPriceQuery(arg.startDate(), arg.productId(), arg.brandId());
        
        List<Price> prices = findPriceUseCase.findByDateProductAndBrand(query);
        
        List<PriceFindOutDTO> response = prices.stream().map(priceFindOutDTOMapper::toDto).toList();
        
        return ResponseEntity.ok(response);
    }
}
