package com.puzzle.ezypayments.messaging;

import com.puzzle.ezypayments.service.WebhookService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WebhookListenerTest {

    @Mock
    private WebhookService webhookService;

    @InjectMocks
    private WebhookListener webhookListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    void handleWebhookProcessesMessageSuccessfully() {
        WebhookMessage message = new WebhookMessage(1L, "http://example.com/webhook");

        webhookListener.handleWebhook(message);

        verify(webhookService, times(1)).notifyWebhooks(1L, "http://example.com/webhook");
    }

    void handleWebhookRetriesOnFailure() {
        WebhookMessage message = new WebhookMessage(1L, "http://example.com/webhook");
        doThrow(new RuntimeException("Error")).when(webhookService).notifyWebhooks(1L, "http://example.com/webhook");

        assertThrows(RuntimeException.class, () -> webhookListener.handleWebhook(message));

        verify(webhookService, times(3)).notifyWebhooks(1L, "http://example.com/webhook");
    }
}