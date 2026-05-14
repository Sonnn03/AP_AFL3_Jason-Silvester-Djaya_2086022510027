package com.example.thymleaf;

import org.springframework.boot.SpringApplication;

public class TestThymleafApplication {

	public static void main(String[] args) {
		SpringApplication.from(ThymleafApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
