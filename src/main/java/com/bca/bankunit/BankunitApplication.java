package com.bca.bankunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class BankunitApplication {

	public static int difficulty = 1;
	
	public static void main(String[] args) {
		SpringApplication.run(BankunitApplication.class, args);
	}
}
