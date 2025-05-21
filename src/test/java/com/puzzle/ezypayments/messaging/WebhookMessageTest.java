package com.puzzle.ezypayments.messaging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WebhookMessageTest {

    @Test
    void webhookMessageConstructorSetsFieldsCorrectly() {
        WebhookMessage message = new WebhookMessage(1L, "http://example.com/webhook");

        assertEquals(1L, message.getPaymentId());
        assertEquals("http://example.com/webhook", message.getUrlWebhook());
    }

    @Test
    void webhookMessageDefaultConstructorInitializesFieldsToNull() {
        WebhookMessage message = new WebhookMessage();

        assertNull(message.getPaymentId());
        assertNull(message.getUrlWebhook());
    }

    @Test
    void webhookMessageSetterUpdatesPaymentId() {
        WebhookMessage message = new WebhookMessage();
        message.setPaymentId(2L);

        assertEquals(2L, message.getPaymentId());
    }

    @Test
    void webhookMessageSetterUpdatesUrlWebhook() {
        WebhookMessage message = new WebhookMessage();
        message.setUrlWebhook("http://example.com/updated-webhook");

        assertEquals("http://example.com/updated-webhook", message.getUrlWebhook());
    }

}