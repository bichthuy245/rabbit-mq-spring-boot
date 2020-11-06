package com.nastech.training.rabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
public class RabbitMQFanoutConfig {

    @Value("${electronic.topic.name}")
    String electronicName;

    @Value("${fashion.topic.name}")
    String fashionName;

    @Value("${doy.topic.name}")
    String doyName;

    @Value("${kid.fashion.topic.name}")
    String kidFashionName;

    @Value("${queue.all.name}")
    String allName;

    // Declare FanOut Exchange
    @Value("${fan.out.training.rabbitmq.exchange}")
    String fanOutExchange;

    // Declare RoutingKey
    @Value("${queue.electronic.routing.key}")
    private String electronicKey;

    @Value("${queue.fashion.routing.key}")
    private String fashionKey;

    @Value("${queue.kid.fashion.routing.key}")
    private String kidFashionKey;

    @Value("${queue.doy.routing.key}")
    private String doyKey;

    @Value("${queue.all.routing.key}")
    private String allQueueKey;

    @Bean
    Queue electronicQueue () {
        return new Queue(electronicName, false);
    }

    @Bean
    Queue fashionQueue () {
        return new Queue(fashionName, false);
    }

    @Bean
    Queue doyQueue () {
        return new Queue(doyName, false);
    }

    @Bean
    Queue kidFashionQueue () {
        return new Queue(kidFashionName, false);
    }

    @Bean
    Queue allQueue () {
        return new Queue(allName, false);
    }

    @Bean
    FanoutExchange exchange () {
        return new FanoutExchange(fanOutExchange);
    }

    @Bean
    Binding electronicBinding (Queue queueName, FanoutExchange exchange) {
        return BindingBuilder.bind(queueName).to(exchange);
    }

    @Bean
    Binding fashionBinding (Queue queueName, FanoutExchange exchange) {
        return BindingBuilder.bind(queueName).to(exchange);
    }

    @Bean
    Binding kidFashionBinding (Queue queueName, FanoutExchange exchange) {
        return BindingBuilder.bind(queueName).to(exchange);
    }

    @Bean
    Binding doyBinding (Queue queueName, FanoutExchange exchange) {
        return BindingBuilder.bind(queueName).to(exchange);
    }

    @Bean
    Binding allBinding (Queue allQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(allQueue).to(exchange);
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
