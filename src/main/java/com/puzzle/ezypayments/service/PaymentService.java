package com.puzzle.ezypayments.service;

import com.puzzle.ezypayments.dto.PaymentDTO;
import com.puzzle.ezypayments.dto.PaymentResponse;
import com.puzzle.ezypayments.entity.Payment;
import com.puzzle.ezypayments.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse processPayment(PaymentDTO paymentDTO) {

        Payment payment = Payment.builder()
                .cvv(paymentDTO.getCvv())
                .expiry(paymentDTO.getExpiry())
                .firstName(paymentDTO.getFirstName())
                .lastName(paymentDTO.getLastName())
                .encryptedCardNumber(paymentDTO.getCardNumber())
                .createdAt(LocalDateTime.now())
                .build();

        var savedPayment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .id(savedPayment.getId())
                .lastName(savedPayment.getLastName())
                .firstName(savedPayment.getFirstName())
                .createdAt(savedPayment.getCreatedAt())
                .build();
    }


}
