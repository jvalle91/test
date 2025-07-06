package com.bcncgroup.testjava2025.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class that represents a brand in the database.
 * This class maps to the BRANDS table and stores brand information.
 */
@Entity
@Table(name = "BRANDS")
public class BrandEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NAME", nullable = false, unique = true, length = 100)
    private String name;
    
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Gets the unique identifier of the brand.
     * 
     * @return the brand ID
     */
	public Long getId() {
		return id;
	}

	/**
     * Sets the unique identifier of the brand.
     * 
     * @param id the brand ID to set
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Gets the name of the brand.
     * 
     * @return the brand name
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the name of the brand.
     * 
     * @param name the brand name to set
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Gets the description of the brand.
     * 
     * @return the brand description
     */
	public String getDescription() {
		return description;
	}

	/**
     * Sets the description of the brand.
     * 
     * @param description the brand description to set
     */
	public void setDescription(String description) {
		this.description = description;
	}

}
