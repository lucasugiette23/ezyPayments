package com.puzzle.ezypayments.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebhookProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public WebhookProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notifyWebhook(Long paymentId, String webhookUrl) {
        WebhookMessage message = new WebhookMessage(paymentId, webhookUrl);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
