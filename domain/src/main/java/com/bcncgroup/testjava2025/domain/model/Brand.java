package com.bcncgroup.testjava2025.domain.model;


/**
 * Value object that represents brand data.
 * This record holds brand information for data transfer.
 */
public record Brand(
	    Long id,
	    String name,
	    String description
		) {

}
