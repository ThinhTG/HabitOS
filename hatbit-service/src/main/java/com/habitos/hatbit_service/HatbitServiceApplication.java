package com.habitos.hatbit_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HatbitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HatbitServiceApplication.class, args);
	}

}
