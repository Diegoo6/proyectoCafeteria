package com.example.empleado_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmpleadoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadoServiceApplication.class, args);
	}

}
