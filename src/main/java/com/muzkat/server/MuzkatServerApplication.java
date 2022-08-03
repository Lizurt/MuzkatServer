package com.muzkat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.muzkat.server")
public class MuzkatServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MuzkatServerApplication.class, args);
	}
}
