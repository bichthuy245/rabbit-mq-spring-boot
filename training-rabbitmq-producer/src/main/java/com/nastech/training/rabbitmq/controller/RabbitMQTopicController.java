package com.nastech.training.rabbitmq.controller;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import com.nastech.training.rabbitmq.service.RabbitMQTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tranning-rabbitmq/topic")
public class RabbitMQTopicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQTopicController.class);

    @Autowired
    private RabbitMQTopicService topicService;

    @GetMapping(value = "/producer")
    public String producer(@RequestBody ShoppingOnline data) {
        ShoppingOnline messageData = new ShoppingOnline();
        messageData.setId(data.getId());
        messageData.setName(data.getName());
        messageData.setPrice(data.getPrice());
        messageData.setGroupName(data.getGroupName());

        topicService.send(messageData);
        LOGGER.info(messageData.toString());
        return "Message sent to the Training RabbitMQ Topic Exchange Successfully";
    }
}
