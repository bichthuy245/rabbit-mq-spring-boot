package com.nastech.training.rabbitmq.service;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RabbitMQTopicService {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${topic.training.rabbitmq.exchange}")
    private String topicExchange;

    @Value("${queue.electronic.routing.key}")
    private String electronicKey;

    @Value("${queue.fashion.routing.key}")
    private String fashionKey;

    @Value("${queue.kid.fashion.routing.key}")
    private String kidFashionKey;

    @Value("${queue.doy.routing.key}")
    private String doyKey;

    @Value("$queue.all.routing.key}")
    private String allQueueKey;

    public void send(ShoppingOnline messagedata) {
        if (Optional.ofNullable(messagedata).isPresent() && Strings.isNotEmpty(messagedata.getName())) {
            String groupName = messagedata.getGroupName().trim().toUpperCase();
            switch (groupName) {
                case "ELECTRONIC":
                    rabbitTemplate.convertAndSend(topicExchange, electronicKey, messagedata);
                    break;
                case "FASHION":
                    rabbitTemplate.convertAndSend(topicExchange, fashionKey, messagedata);
                    break;
                default:
                    rabbitTemplate.convertAndSend(topicExchange, allQueueKey, messagedata);
                    break;
            }
            System.out.println("Send message with Topic Exchange: " + messagedata);
        }
    }
}
