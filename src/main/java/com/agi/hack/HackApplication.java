package com.agi.hack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackApplication.class, args);
	}

}
