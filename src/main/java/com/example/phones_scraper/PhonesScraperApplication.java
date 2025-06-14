package com.example.phones_scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.phones_scraper.repository")
@EntityScan("com.example.phones_scraper.model")
public class PhonesScraperApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhonesScraperApplication.class, args);
	}
}
