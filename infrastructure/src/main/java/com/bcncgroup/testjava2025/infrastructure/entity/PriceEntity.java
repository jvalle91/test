package com.bcncgroup.testjava2025.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class that represents a price in the database.
 * This class maps to the PRICES table and stores price information.
 */
@Table(name = "PRICES")
@Entity
public class PriceEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_ID")
    private BrandEntity brand;
    
    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;
    
    @Column(name = "PRICE_LIST", nullable = false)
    private Integer priceList;
    
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;
    
    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;
    
    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "CURR", nullable = false, length = 3)
    private String currency;

    /**
     * Gets the unique identifier of the price.
     * 
     * @return the price ID
     */
	public Long getId() {
		return id;
	}

	/**
     * Sets the unique identifier of the price.
     * 
     * @param id the price ID to set
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Gets the brand associated with this price.
     * 
     * @return the brand entity
     */
	public BrandEntity getBrand() {
		return brand;
	}

	/**
     * Sets the brand associated with this price.
     * 
     * @param brandId the brand entity to set
     */
	public void setBrand(BrandEntity brandId) {
		this.brand = brandId;
	}

	/**
     * Gets the start date when this price becomes valid.
     * 
     * @return the start date
     */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
     * Sets the start date when this price becomes valid.
     * 
     * @param startDate the start date to set
     */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
     * Gets the end date when this price stops being valid.
     * 
     * @return the end date
     */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
     * Sets the end date when this price stops being valid.
     * 
     * @param endDate the end date to set
     */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	/**
     * Gets the price list identifier.
     * 
     * @return the price list ID
     */
	public Integer getPriceList() {
		return priceList;
	}

	/**
     * Sets the price list identifier.
     * 
     * @param priceList the price list ID to set
     */
	public void setPriceList(Integer priceList) {
		this.priceList = priceList;
	}

	/**
     * Gets the product identifier.
     * 
     * @return the product ID
     */
	public Long getProductId() {
		return productId;
	}

	/**
     * Sets the product identifier.
     * 
     * @param productId the product ID to set
     */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
     * Gets the priority of this price.
     * Higher numbers mean higher priority.
     * 
     * @return the priority value
     */
	public Integer getPriority() {
		return priority;
	}

	/**
     * Sets the priority of this price.
     * 
     * @param priority the priority value to set
     */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
     * Gets the final price value.
     * 
     * @return the price amount
     */
	public BigDecimal getPrice() {
		return price;
	}

	/**
     * Sets the final price value.
     * 
     * @param price the price amount to set
     */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
     * Gets the currency code for this price.
     * 
     * @return the currency code (ISO format)
     */
	public String getCurrency() {
		return currency;
	}

	/**
     * Sets the currency code for this price.
     * 
     * @param currency the currency code to set (ISO format)
     */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
