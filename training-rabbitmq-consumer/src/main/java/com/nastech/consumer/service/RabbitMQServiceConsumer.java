package com.nastech.consumer.service;

import com.nastech.consumer.entity.ShoppingOnline;
import com.nastech.consumer.exception.InvalidPriceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQServiceConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQServiceConsumer.class);

    @RabbitListener(queues = "topic_electronic_name")
    public void recievedMessage(ShoppingOnline shoppingOnline) throws InvalidPriceException {
        logger.info("Recieved Message From RabbitMQ: " + shoppingOnline);
        System.out.println("Recieved Message From RabbitMQ: " + shoppingOnline);
        if ( shoppingOnline.getPrice() < 0) {
            throw new InvalidPriceException();
        }
    }

    @RabbitListener(queues = "topic_doy_name")
    public void recievedMessageFanoutExchange(ShoppingOnline shoppingOnline) throws InvalidPriceException {
        logger.info("Recieved Message From topic_doy_name: " + shoppingOnline);
        System.out.println("Recieved Message From topic_doy_name: " + shoppingOnline);
        if ( shoppingOnline.getPrice() < 0) {
            throw new InvalidPriceException();
        }
    }
}
