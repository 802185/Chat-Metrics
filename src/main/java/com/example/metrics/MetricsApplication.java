package com.example.metrics;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Queue;

@SpringBootApplication
public class MetricsApplication {
	public static void main(String[] args) {
		SpringApplication.run(MetricsApplication.class, args);
	}
}