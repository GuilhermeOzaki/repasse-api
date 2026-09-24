package com.portfolio.repasse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RepasseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepasseApiApplication.class, args);
	}


}
