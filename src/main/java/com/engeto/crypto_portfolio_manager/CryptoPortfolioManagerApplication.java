package com.engeto.crypto_portfolio_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoPortfolioManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoPortfolioManagerApplication.class, args);
	}

}
