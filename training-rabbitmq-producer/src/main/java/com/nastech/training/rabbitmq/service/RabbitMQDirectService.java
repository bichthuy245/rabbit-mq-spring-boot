package com.nastech.training.rabbitmq.service;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RabbitMQDirectService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${direct.training.rabbitmq.exchange}")
    private String directExchange;

    @Value("${queue.camera.routing.key}")
    private String cameraKey;

    @Value("${queue.camera.routing.key}")
    private String watchKey;

    @Value("$queue.all.routing.key}")
    private String allQueueKey;

    public void send (ShoppingOnline messagedata) {
        if ( Optional.ofNullable(messagedata).isPresent() && Strings.isNotEmpty(messagedata.getName()) ) {
            String groupName = messagedata.getGroupName().trim().toUpperCase();
            switch (groupName) {
                case "CAMERA":
                    rabbitTemplate.convertAndSend(directExchange, cameraKey, messagedata);
                    break;
                case "WATCH":
                    rabbitTemplate.convertAndSend(directExchange, watchKey, messagedata);
                    break;
                default:
                    rabbitTemplate.convertAndSend(directExchange, allQueueKey, messagedata);
                    break;
            }
            System.out.println("Send message with Direct Exchange: " + messagedata);
        }
    }
}
