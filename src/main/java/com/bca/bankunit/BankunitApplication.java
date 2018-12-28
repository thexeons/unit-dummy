package com.bca.bankunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bca.bankunit.model.Block;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class BankunitApplication {

	public static int difficulty = 1;
	
	public static void main(String[] args) {
		Block.master1 = args[0];
		Block.master2 = args[1];
		Block.master3 = args[2];
		Block.master4 = args[3];
		Block.master5 = args[4];
		
		Block.instance1 = args[5];
		Block.instance2 = args[6];
		Block.instance3 = args[7];
		Block.instance4 = args[8];

		SpringApplication.run(BankunitApplication.class, args);
	}
}
