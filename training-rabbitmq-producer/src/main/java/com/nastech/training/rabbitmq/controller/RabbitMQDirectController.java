package com.nastech.training.rabbitmq.controller;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import com.nastech.training.rabbitmq.service.RabbitMQDirectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tranning-rabbitmq/direct")
public class RabbitMQDirectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQDirectController.class);

    @Autowired
    private RabbitMQDirectService directService;

    @GetMapping(value = "/producer")
    public String producer(@RequestBody ShoppingOnline data) {
        ShoppingOnline messageData = new ShoppingOnline();
        messageData.setId(data.getId());
        messageData.setName(data.getName());
        messageData.setPrice(data.getPrice());
        messageData.setGroupName(data.getGroupName());

        directService.send(messageData);

        return "Message sent to the Training RabbitMQ Direct Exchange Successfully";
    }
}
