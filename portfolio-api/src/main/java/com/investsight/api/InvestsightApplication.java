package com.investsight.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InvestsightApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvestsightApplication.class, args);
	}
}