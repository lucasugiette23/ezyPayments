package com.puzzle.ezypayments.service;

import com.puzzle.ezypayments.dto.WebhookDTO;
import com.puzzle.ezypayments.entity.Webhook;
import com.puzzle.ezypayments.repository.WebhookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@RequiredArgsConstructor
@Service
public class WebhookService {

    @Autowired
    private RestTemplate restTemplate;

    private final WebhookRepository webhookRepository;

    @Retryable(
        value = {RestClientException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    public void notifyWebhooks(Long paymentId, String urlWebhook) {
        List<Webhook> webhookList = webhookRepository.findAll();
        for (Webhook webhook : webhookList) {
            try {
                restTemplate.postForEntity(webhook.getEndpoint(), webhook.getClientId(), Void.class);
            } catch (Exception e) {
                //TODO opcional: logar e colocar numa fila morta
            }
        }
    }

    public WebhookDTO registryWebhooks(WebhookDTO webhookDTO) {

        var webhook = Webhook.builder()
                .endpoint(webhookDTO.getEndpoint())
                .clientId(webhookDTO.getClientId())
                .eventType(webhookDTO.getEventType())
                .build();

        webhookRepository.save(webhook);

        return webhookDTO;

    }

}
