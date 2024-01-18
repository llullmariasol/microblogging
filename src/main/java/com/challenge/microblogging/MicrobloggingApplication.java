package com.challenge.microblogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "com.challenge.microblogging.repository")
public class MicrobloggingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicrobloggingApplication.class, args);
	}

}
