package com.daxasoft.JWTAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JwtAuthenticationApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(JwtAuthenticationApplication.class, args); 
		
	}

}
