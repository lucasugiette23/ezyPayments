package com.puzzle.ezypayments.controller;

import com.puzzle.ezypayments.dto.PaymentDTO;
import com.puzzle.ezypayments.dto.PaymentResponse;
import com.puzzle.ezypayments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentDTO paymentDTO) {
        var returnPayment = paymentService.processPayment(paymentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(returnPayment);
    }

}
