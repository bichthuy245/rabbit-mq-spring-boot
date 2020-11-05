package rabbitmqtraining.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmqtraining.consumer.entity.ShoppingOnline;
import rabbitmqtraining.consumer.exception.InvalidPriceException;

@Component
public class RabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "topic_electronic_name")
    public void recievedMessage(ShoppingOnline shoppingOnline) throws InvalidPriceException {
        logger.info("Recieved Message From RabbitMQ: " + shoppingOnline);
        System.out.println("Recieved Message From RabbitMQ: " + shoppingOnline);
        if ( shoppingOnline.getPrice() < 0) {
            throw new InvalidPriceException();
        }
    }
}
