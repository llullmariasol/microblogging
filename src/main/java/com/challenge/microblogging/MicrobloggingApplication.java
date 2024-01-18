package com.challenge.microblogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(scanBasePackages = "com.challenge")
@EnableMongoAuditing()
public class MicrobloggingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicrobloggingApplication.class, args);
	}

}
