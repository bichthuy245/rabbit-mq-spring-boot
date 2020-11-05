package com.nastech.training.rabbitmq.service;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;

@Service
public class RabbitMQFanOutService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${fan.out.training.rabbitmq.exchange}")
    private String fanOutexchange;

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

    public void send (ShoppingOnline messagedata) {
        if ( Optional.ofNullable(messagedata).isPresent() && Strings.isNotEmpty(messagedata.getName()) ) {
            String groupName = messagedata.getGroupName().trim().toUpperCase();
            switch (groupName) {
                case "ELECTRONIC":
                    rabbitTemplate.convertAndSend(fanOutexchange, electronicKey, messagedata);
                    break;
                case "FASHION":
                    rabbitTemplate.convertAndSend(fanOutexchange, fashionKey, messagedata);
                    break;
                case "KIDFASHION":
                    rabbitTemplate.convertAndSend(fanOutexchange, kidFashionKey, messagedata);
                    break;
                case "DOY":
                    rabbitTemplate.convertAndSend(fanOutexchange, doyKey, messagedata);
                    break;
                default:
                    rabbitTemplate.convertAndSend(fanOutexchange, allQueueKey, messagedata);
                    break;
            }
            System.out.println("Send message with FanOut Exchange: " + messagedata);
        }
    }
}
