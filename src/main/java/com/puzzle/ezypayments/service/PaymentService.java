package com.puzzle.ezypayments.service;

import com.puzzle.ezypayments.dto.PaymentDTO;
import com.puzzle.ezypayments.dto.PaymentResponse;
import com.puzzle.ezypayments.entity.Payment;
import com.puzzle.ezypayments.entity.Webhook;
import com.puzzle.ezypayments.messaging.WebhookProducer;
import com.puzzle.ezypayments.repository.PaymentRepository;
import com.puzzle.ezypayments.repository.WebhookRepository;
import com.puzzle.ezypayments.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EncryptionUtil encryptionUtil;
    private final WebhookProducer webhookProducer;
    private final WebhookRepository webhookRepository;

    public PaymentResponse processPayment(PaymentDTO paymentDTO) {

        var encryptedCardNumber = encryptionUtil.encrypt(paymentDTO.getCardNumber());

        Payment payment = Payment.builder()
                .cvv(paymentDTO.getCvv())
                .expiry(paymentDTO.getExpiry())
                .firstName(paymentDTO.getFirstName())
                .lastName(paymentDTO.getLastName())
                .encryptedCardNumber(encryptedCardNumber)
                .createdAt(LocalDateTime.now())
                .build();

        var savedPayment = mapToPaymentResponse(paymentRepository.save(payment));
        List<Webhook> webhooks = webhookRepository.findAllByClientId(String.valueOf(savedPayment.getId()));

        for (Webhook webhook : webhooks) {
            webhookProducer.notifyWebhook(savedPayment.getId(), webhook.getEndpoint());
        }

        return savedPayment;
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .firstName(payment.getFirstName())
                .lastName(payment.getLastName())
                .createdAt(payment.getCreatedAt())
                .build();
    }


}
