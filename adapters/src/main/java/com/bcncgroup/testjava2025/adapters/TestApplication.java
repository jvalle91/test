package com.bcncgroup.testjava2025.adapters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 * This class starts the REST API application.
 */
@SpringBootApplication(scanBasePackages = "com.bcncgroup.testjava2025")
@EnableAutoConfiguration
public class TestApplication {
	
	/**
     * Main method to start the application.
     * Access Swagger UI at: http://localhost:8080/swagger-ui/index.html
     * 
     * @param args command line arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
}
