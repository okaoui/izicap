package com.homework.izicap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class IzicapApplication {

	public static void main(String[] args) {
		SpringApplication.run(IzicapApplication.class, args);
	}



}
