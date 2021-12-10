package com.zkazemi.rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DefaultExchangeConfig
{
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${rabbitmq.default.queue}")
    private String DEFAULT_QUEUE;


    Queue createQueue()
    {
        return new Queue(DEFAULT_QUEUE, true, false, false);
    }

    @Bean
    public AmqpTemplate defaultExchange(ConnectionFactory connectionFactory, MessageConverter messageConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        //in default queue-> routing key is queue name
        rabbitTemplate.setRoutingKey(DEFAULT_QUEUE);

        return rabbitTemplate;
    }

    // it will be called just once
    @PostConstruct
    public void init()
    {
        amqpAdmin.declareQueue(createQueue());
    }
}
