package com.bcncgroup.testjava2025.infrastructure.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for persistence layer.
 * This class sets up JPA repositories and entity scanning.
 */
@Configuration
@EntityScan(basePackages = "com.bcncgroup.testjava2025.infrastructure.entity")
@EnableJpaRepositories(basePackages = "com.bcncgroup.testjava2025.infrastructure.repository")
public class PersistenceConf {
	
	// No additional configuration was required

}
