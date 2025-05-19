package com.puzzle.ezypayments.messaging;

import com.puzzle.ezypayments.service.WebhookService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class WebhookListener {

    private final WebhookService webhookService;

    public WebhookListener(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @RabbitListener(queues = "${webhook.queue.name}")
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000, multiplier = 2)
    )
    public void handleWebhook(WebhookMessage message) {
        webhookService.notifyWebhooks(message.getPaymentId(), message.getUrlWebhook());
    }

}
