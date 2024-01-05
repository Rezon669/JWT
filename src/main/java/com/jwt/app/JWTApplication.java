package com.jwt.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication

public class JWTApplication {

	public static void main(String[] args) {
		SpringApplication.run(JWTApplication.class, args);
	}

}
