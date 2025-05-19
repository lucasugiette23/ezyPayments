package com.puzzle.ezypayments.controller;

import com.puzzle.ezypayments.dto.WebhookDTO;
import com.puzzle.ezypayments.service.WebhookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class WebhookControllerTest {

    @Mock
    private WebhookService webhookService;

    @InjectMocks
    private WebhookController webhookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processWebhookReturnsOkResponseWithValidWebhookDTO() {
        WebhookDTO webhookDTO = WebhookDTO.builder()
                .endpoint("http://example.com/webhook")
                .clientId("client123")
                .eventType("PAYMENT")
                .build();
        when(webhookService.registryWebhooks(any(WebhookDTO.class))).thenReturn(webhookDTO);

        ResponseEntity<WebhookDTO> response = webhookController.processWebhook(webhookDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("http://example.com/webhook", response.getBody().getEndpoint());
        assertEquals("client123", response.getBody().getClientId());
        assertEquals("PAYMENT", response.getBody().getEventType());
    }

    @Test
    void processWebhookThrowsExceptionForInvalidWebhookDTO() {
        WebhookDTO invalidWebhookDTO = WebhookDTO.builder()
                .endpoint("invalid-url")
                .clientId("")
                .eventType("INVALID_EVENT")
                .build();

        when(webhookService.registryWebhooks(any(WebhookDTO.class))).thenThrow(new IllegalArgumentException("Invalid WebhookDTO"));

        assertThrows(IllegalArgumentException.class, () -> webhookController.processWebhook(invalidWebhookDTO));
    }
}