package com.nastech.training.rabbitmq.controller;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tranning-rabbitmq/direct")
public class RabbitMQDirectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQDirectController.class);

    @Autowired
    private AmqpTemplate mqpTemplate;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
                           @RequestBody ShoppingOnline data) {
        ShoppingOnline messageData = new ShoppingOnline();
        messageData.setId(data.getId());
        messageData.setName(data.getName());
        messageData.setPrice(data.getPrice());
        messageData.setGroupName(data.getGroupName());

        mqpTemplate.convertAndSend(exchange, routingKey, messageData);

        return "Message sent to the Training RabbitMQ Direct Exchange Successfully";
    }
}
