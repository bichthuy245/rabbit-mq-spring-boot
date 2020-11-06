package com.nastech.training.rabbitmq.service;

import com.nastech.training.rabbitmq.entity.ShoppingOnline;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RabbitMQFanOutService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${fan.out.training.rabbitmq.exchange}")
    private String fanOutexchange;

    public void send(ShoppingOnline messagedata) {
        if (Optional.ofNullable(messagedata).isPresent() && Strings.isNotEmpty(messagedata.getName())) {
            String groupName = messagedata.getGroupName().trim().toUpperCase();
            switch (groupName) {
                case "KIDFASHION":
                    rabbitTemplate.convertAndSend(fanOutexchange, "", messagedata);
                    break;
                case "DOY":
                    rabbitTemplate.convertAndSend(fanOutexchange, "", messagedata);
                    break;
                default:
                    rabbitTemplate.convertAndSend(fanOutexchange, "", messagedata);
                    break;
            }
            System.out.println("Send message with FanOut Exchange: " + messagedata);
        }
    }
}
