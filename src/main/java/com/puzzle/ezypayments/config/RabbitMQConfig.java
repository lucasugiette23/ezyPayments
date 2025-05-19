package com.puzzle.ezypayments.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "payment.exchange";
    public static final String DLQ = "payment.webhook.dlq";
    public static final String ROUTING_KEY = "payment.notify";
    public static final String DLQ_ROUTING_KEY = "payment.dlq";

    @Value("${webhook.queue.name}")
    private String queueName;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue mainQueue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(mainQueue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlqQueue())
                .to(exchange())
                .with(DLQ_ROUTING_KEY);
    }
}