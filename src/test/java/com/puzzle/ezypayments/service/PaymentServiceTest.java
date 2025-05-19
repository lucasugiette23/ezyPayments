package com.puzzle.ezypayments.service;

import com.puzzle.ezypayments.dto.PaymentDTO;
import com.puzzle.ezypayments.dto.PaymentResponse;
import com.puzzle.ezypayments.entity.Payment;
import com.puzzle.ezypayments.entity.Webhook;
import com.puzzle.ezypayments.messaging.WebhookProducer;
import com.puzzle.ezypayments.repository.PaymentRepository;
import com.puzzle.ezypayments.repository.WebhookRepository;
import com.puzzle.ezypayments.util.EncryptionUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private EncryptionUtil encryptionUtil;

    @Mock
    private WebhookProducer webhookProducer;

    @Mock
    private WebhookRepository webhookRepository;

    @Test
    void processPaymentReturnsPaymentResponseWhenValidPaymentDTOProvided() {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .cardNumber("1234567890123456")
                .cvv("123")
                .expiry("12/25")
                .firstName("John")
                .lastName("Doe")
                .build();
        Payment payment = Payment.builder()
                .id(1L)
                .cvv("123")
                .expiry("12/25")
                .firstName("John")
                .lastName("Doe")
                .encryptedCardNumber("encryptedCardNumber")
                .createdAt(LocalDateTime.now())
                .build();
        PaymentResponse expectedResponse = PaymentResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(payment.getCreatedAt())
                .build();

        when(encryptionUtil.encrypt(paymentDTO.getCardNumber())).thenReturn("encryptedCardNumber");
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(webhookRepository.findAllByClientId("1")).thenReturn(List.of());

        PaymentResponse response = paymentService.processPayment(paymentDTO);

        assertEquals(expectedResponse, response);
    }

    @Test
    void processPaymentNotifiesWebhooksWhenWebhooksExist() {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .cardNumber("1234567890123456")
                .cvv("123")
                .expiry("12/25")
                .firstName("John")
                .lastName("Doe")
                .build();
        Payment payment = Payment.builder()
                .id(1L)
                .cvv("123")
                .expiry("12/25")
                .firstName("John")
                .lastName("Doe")
                .encryptedCardNumber("encryptedCardNumber")
                .createdAt(LocalDateTime.now())
                .build();
        Webhook webhook = Webhook.builder()
                .clientId("1")
                .endpoint("http://example.com/webhook")
                .build();

        when(encryptionUtil.encrypt(paymentDTO.getCardNumber())).thenReturn("encryptedCardNumber");
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(webhookRepository.findAllByClientId("1")).thenReturn(List.of(webhook));

        paymentService.processPayment(paymentDTO);

        verify(webhookProducer).notifyWebhook(1L, "http://example.com/webhook");
    }

    @Test
    void processPaymentThrowsExceptionWhenPaymentDTOIsNull() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.processPayment(null));
    }
}