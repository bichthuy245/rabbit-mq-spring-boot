package com.nastech.training.rabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQDirectExchangeConfig {

    @Value("${camera.topic.name}")
    String cameraName;

    @Value("${watch.topic.name}")
    String watchName;

    @Value("${direct.queue.all.name}")
    String allName;

    @Value("${deadLetter.rabbitmq.queue}")
    String deadLetterQueueName;

    @Value("${direct.training.rabbitmq.exchange}")
    String directExchange;

    @Value("${deadLetter.rabbitmq.exchange}")
    String deadLetterExchange;

    // Declare RoutingKey
    @Value("${queue.watch.routing.key}")
    private String watchKey;

    @Value("${queue.camera.routing.key}")
    private String cameraKey;

    @Value("${deadLetter.rabbitmq.routing.key}")
    private String deadRoutingKey;

    @Value("${queue.all.routing.key}")
    private String allQueueKey;

    @Bean
    Queue cameraQueue () {
        return QueueBuilder.durable(cameraName)
                           .withArgument("x-dead-letter-exchange", deadLetterExchange)
                           .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                           .withArgument("x-message-ttl", 60000).build();
        //        return new Queue(doyName, false);
    }

    @Bean
    Queue watchQueue () {
        return QueueBuilder.durable(watchName)
                           .withArgument("x-dead-letter-exchange", deadLetterExchange)
                           .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                           .withArgument("x-message-ttl", 60000).build();
        //        return new Queue(kidFashionName, false);
    }

    @Bean
    Queue allQueue () {
        return QueueBuilder.durable(allName)
                           .withArgument("x-dead-letter-exchange", deadLetterExchange)
                           .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                           .withArgument("x-message-ttl", 60000).build();
        //        return new Queue(allName, false);
    }

    @Bean
    Queue deadLetterQueue () {
        return QueueBuilder.durable(deadLetterQueueName).build();
    }

    @Bean
    DirectExchange directExchange () {
        return new DirectExchange(directExchange);
    }

    @Bean
    @Primary
    DirectExchange deadLetterExchange () {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    Binding cameraBinding (Queue cameraQueue, DirectExchange exchange) {
        return BindingBuilder.bind(cameraQueue).to(exchange).with(cameraKey);
    }

    @Bean
    Binding watchBinding (Queue watchQueue, DirectExchange exchange) {
        return BindingBuilder.bind(watchQueue).to(exchange).with(watchKey);
    }

    @Bean
    Binding allBinding (Queue allQueue, DirectExchange exchange) {
        return BindingBuilder.bind(allQueue).to(exchange).with(allQueueKey);
    }

    @Bean
    Binding DLQbinding () {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter () {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer (ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate (ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
