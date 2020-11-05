package com.nastech.training.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {

    @Value("${training.rabbitmq.queue}")
    String queueName;

    @Value("${deadLetter.rabbitmq.queue}")
    String deadLetterqueueName;

    @Value("${training.rabbitmq.exchange}")
    String exchange;

    @Value("${deadLetter.rabbitmq.exchange}")
    String deadLetterExchange;

    @Value("${training.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${deadLetter.rabbitmq.routingkey}")
    private String deadRoutingkey;

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(deadLetterqueueName).build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queueName).withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingkey).build();
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadRoutingkey);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
