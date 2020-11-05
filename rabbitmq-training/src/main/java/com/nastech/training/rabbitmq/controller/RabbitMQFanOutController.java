package com.nastech.training.rabbitmq.controller;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tranning-rabbitmq/fanout")
public class RabbitMQFanOutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQFanOutController.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
                           @RequestBody ShoppingOnline data) {
        ShoppingOnline messageData = new ShoppingOnline();
        messageData.setId(data.getId());
        messageData.setName(data.getName());
        messageData.setPrice(data.getPrice());
        messageData.setGroupName(data.getGroupName());

        amqpTemplate.convertAndSend(exchange, routingKey, messageData);

        LOGGER.info(messageData.toString());
        return "Message sent to the Training RabbitMQ FanOut Exchange Successfully";
    }
}
