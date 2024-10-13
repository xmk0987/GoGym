package com.onniviti.gogym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("com.onniviti.gogym.*")
@ComponentScan(basePackages = { "com.onniviti.gogym.*" })
@EntityScan("com.onniviti.gogym.*")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
