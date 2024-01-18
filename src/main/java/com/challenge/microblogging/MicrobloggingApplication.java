package com.challenge.microblogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.challenge")
public class MicrobloggingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicrobloggingApplication.class, args);
	}

}
