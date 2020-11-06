package com.nastech.training.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQFanOutExchangeConfig {

    @Value("${doy.topic.name}")
    String doyName;

    @Value("${kid.fashion.topic.name}")
    String kidFashionName;

    @Value("${fan.out.queue.all.name}")
    String allName;

    // Declare FanOut Exchange
    @Value("${fan.out.training.rabbitmq.exchange}")
    private String fanOutExchange;

    @Bean
    Queue doyQueue() {
        return new Queue(doyName, false);
    }

    @Bean
    Queue kidFashionQueue() {
        return new Queue(kidFashionName, false);
    }

    @Bean
    Queue allQueue() {
        return new Queue(allName, false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(fanOutExchange);
    }

    @Bean
    Binding kidFashionBinding(Queue kidFashionQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(kidFashionQueue).to(exchange);
    }

    @Bean
    Binding doyBinding(Queue doyQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(doyQueue).to(exchange);
    }

    @Bean
    Binding allBinding(Queue allQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(allQueue).to(exchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
