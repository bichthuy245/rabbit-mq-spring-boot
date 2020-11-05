package com.nastech.training.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

@SpringBootApplication
public class RabbitmqTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqTrainingApplication.class, args);
	}

}
