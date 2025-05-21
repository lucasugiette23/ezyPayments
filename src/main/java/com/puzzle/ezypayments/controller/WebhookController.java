package com.puzzle.ezypayments.controller;

import com.puzzle.ezypayments.dto.WebhookDTO;
import com.puzzle.ezypayments.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping
    public ResponseEntity<WebhookDTO> processWebhook(@RequestBody WebhookDTO webhookDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(webhookService.registryWebhooks(webhookDTO));
    }
}
