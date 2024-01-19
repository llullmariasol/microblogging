package com.challenge.microblogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.challenge")
@EnableMongoAuditing()
@EnableMongoRepositories(basePackages = "com.challenge.microblogging.repository")
public class MicrobloggingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicrobloggingApplication.class, args);
	}

}
