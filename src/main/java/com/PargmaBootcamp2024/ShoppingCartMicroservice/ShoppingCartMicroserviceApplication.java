package com.PargmaBootcamp2024.ShoppingCartMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShoppingCartMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartMicroserviceApplication.class, args);
	}

}
