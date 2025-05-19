package com.puzzle.ezypayments.service;

import com.puzzle.ezypayments.dto.WebhookDTO;
import com.puzzle.ezypayments.entity.Webhook;
import com.puzzle.ezypayments.repository.WebhookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WebhookServiceTest {

    @Mock
    private WebhookRepository webhookRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WebhookService webhookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void notifyWebhooksSuccessfully() {
        Webhook webhook = new Webhook();
        webhook.setEndpoint("http://example.com/webhook");
        webhook.setClientId("client123");

        when(webhookRepository.findAll()).thenReturn(List.of(webhook));

        webhookService.notifyWebhooks(1L, "http://example.com/webhook");

        verify(restTemplate, times(1)).postForEntity(eq("http://example.com/webhook"), eq("client123"), eq(Void.class));
    }

    @Test
    void notifyWebhooksRetriesOnFailure() {
        Webhook webhook = new Webhook();
        webhook.setEndpoint("http://example.com/webhook");
        webhook.setClientId("client123");

        when(webhookRepository.findAll()).thenReturn(List.of(webhook));
        doThrow(new RestClientException("Error")).when(restTemplate).postForEntity(anyString(), any(), eq(Void.class));

        assertThrows(RestClientException.class, () -> webhookService.notifyWebhooks(1L, "http://example.com/webhook"));

        verify(restTemplate, times(3)).postForEntity(eq("http://example.com/webhook"), eq("client123"), eq(Void.class));
    }

    @Test
    void registryWebhooksSuccessfully() {
        WebhookDTO webhookDTO = WebhookDTO.builder()
                        .eventType("PAYMENT")
                        .clientId("client123")
                        .endpoint("http://example.com/webhook")
                                .build();

        Webhook webhook = Webhook.builder()
                .endpoint("http://example.com/webhook")
                .clientId("client123")
                .eventType("PAYMENT")
                .build();

        when(webhookRepository.save(any(Webhook.class))).thenReturn(webhook);

        WebhookDTO result = webhookService.registryWebhooks(webhookDTO);

        assertEquals("http://example.com/webhook", result.getEndpoint());
        assertEquals("client123", result.getClientId());
        assertEquals("PAYMENT", result.getEventType());
    }
}