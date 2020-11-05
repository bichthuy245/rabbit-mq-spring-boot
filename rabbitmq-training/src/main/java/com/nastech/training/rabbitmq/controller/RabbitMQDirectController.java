package com.nastech.training.rabbitmq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import com.nastech.training.rabbitmq.service.RabbitMQDirectService;

@RestController
@RequestMapping(value = "/tranning-rabbitmq/direct")
public class RabbitMQDirectController {
    private static final Logger                LOGGER = LoggerFactory.getLogger(RabbitMQTopicController.class);

    @Autowired
    private           RabbitMQDirectService rabbitMQDirectService;

    @GetMapping(value = "/producer")
    public String producer(@RequestBody ShoppingOnline data) {
        ShoppingOnline messageData = new ShoppingOnline();
        messageData.setId(data.getId());
        messageData.setName(data.getName());
        messageData.setPrice(data.getPrice());
        messageData.setGroupName(data.getGroupName());

        rabbitMQDirectService.send(messageData);

        LOGGER.info(messageData.toString());
        return "Message sent to the Training RabbitMQ Direct Exchange Successfully";
    }
}
