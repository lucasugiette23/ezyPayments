package com.puzzle.ezypayments.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @PostMapping
    public String processWebhook() {
        // Logic to process payment
        return "Payment processed successfully!";
    }
}
