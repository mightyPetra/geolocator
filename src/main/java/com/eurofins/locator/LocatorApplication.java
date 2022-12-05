package com.eurofins.locator;

import com.eurofins.locator.configs.Properties;
import com.eurofins.locator.persistance.repositories.IpRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IpRepository.class)
@EnableConfigurationProperties(Properties.class)
public class LocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocatorApplication.class, args);
	}

}