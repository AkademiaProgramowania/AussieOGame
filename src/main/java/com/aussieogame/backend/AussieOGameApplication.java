package com.aussieogame.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AussieOGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(AussieOGameApplication.class, args);
	}
}
