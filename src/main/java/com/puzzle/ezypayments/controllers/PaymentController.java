package com.puzzle.ezypayments.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {


    @PostMapping
    public String processPayment() {
        // Logic to process payment
        return "Payment processed successfully!";
    }

}
