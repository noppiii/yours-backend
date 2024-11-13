package com.example.tes.coding.yours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TesCodingYoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesCodingYoursApplication.class, args);
	}

}
