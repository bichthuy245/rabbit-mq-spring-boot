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
public class RabbitMQTopicExchangeConfig {

    @Value("${electronic.topic.name}")
    String electronicName;

    @Value("${fashion.topic.name}")
    String fashionName;

    @Value("${topic.queue.all.name}")
    String allName;

    @Value("${deadLetter.rabbitmq.queue}")
    String deadLetterQueueName;

    @Value("${topic.training.rabbitmq.exchange}")
    String topicExchange;

    @Value("${deadLetter.rabbitmq.exchange}")
    String deadLetterExchange;

    // Declare RoutingKey
    @Value("${queue.electronic.routing.key}")
    private String electronicKey;

    @Value("${queue.fashion.routing.key}")
    private String fashionKey;

    @Value("${queue.all.routing.key}")
    private String allQueueKey;

    @Value("${deadLetter.rabbitmq.routing.key}")
    private String deadRoutingKey;

    @Bean
    Queue electronicQueue() {
        return QueueBuilder.durable(electronicName)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                .withArgument("x-message-ttl", 60000).build();
        //        return new Queue(electronicName, false);
    }

    @Bean
    Queue fashionQueue() {
        return QueueBuilder.durable(fashionName)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                .withArgument("x-message-ttl", 60000).build();
        //        return new Queue(fashionName, false);
    }

    @Bean
    Queue allQueue() {
        return QueueBuilder.durable(allName)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadRoutingKey)
                .withArgument("x-message-ttl", 60000).build();
        //        return new Queue(allName, false);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(deadLetterQueueName).build();
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    TopicExchange deadLetterExchange() {
        return new TopicExchange(deadLetterExchange);
    }

    @Bean
    Binding electronicBinding(Queue electronicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(electronicQueue).to(topicExchange).with(electronicKey);
    }

    @Bean
    Binding fashionBinding(Queue fashionQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(fashionQueue).to(topicExchange).with(fashionKey);
    }

    @Bean
    Binding allBinding(Queue allQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(allQueue).to(topicExchange).with(allQueueKey);
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadRoutingKey);
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
