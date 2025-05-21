package com.puzzle.ezypayments.controller;

import com.puzzle.ezypayments.dto.PaymentDTO;
import com.puzzle.ezypayments.dto.PaymentResponse;
import com.puzzle.ezypayments.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processPaymentReturnsOkResponseWithValidPaymentDTO() {

        var createdAt = LocalDateTime.now();

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .cvv("123")
                .cardNumber("1234567890123456")
                .expiry("12/25")
                .firstName("Lucas")
                .lastName("Silva")
                .build();


        PaymentResponse paymentResponse = PaymentResponse.builder()
                .firstName("Lucas")
                .lastName("Silva")
                .createdAt(createdAt)
                .build();

        when(paymentService.processPayment(any(PaymentDTO.class))).thenReturn(paymentResponse);

        ResponseEntity<PaymentResponse> response = paymentController.processPayment(paymentDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Lucas", response.getBody().getFirstName());
        assertEquals("Silva", response.getBody().getLastName());
        assertEquals(createdAt, response.getBody().getCreatedAt());
    }

    @Test
    void processPaymentThrowsExceptionForInvalidPaymentDTO() {
        PaymentDTO invalidPaymentDTO = PaymentDTO.builder()
                .cvv("123")
                .cardNumber("1234567890123456")
                .expiry("12/25")
                .firstName("Lucas")
                .lastName("Silva")
                .build();

        when(paymentService.processPayment(any(PaymentDTO.class))).thenThrow(new IllegalArgumentException("Invalid PaymentDTO"));

        assertThrows(IllegalArgumentException.class, () -> paymentController.processPayment(invalidPaymentDTO));
    }

    @Test
    void processPaymentReturnsBadRequestWhenPaymentDTOIsNull() {
        assertThrows(IllegalArgumentException.class, () -> paymentController.processPayment(null));
    }

    @Test
    void processPaymentReturnsBadRequestForInvalidCardNumber() {
        PaymentDTO invalidPaymentDTO = PaymentDTO.builder()
                .cvv("123")
                .cardNumber("invalid_card_number")
                .expiry("12/25")
                .firstName("Lucas")
                .lastName("Silva")
                .build();

        when(paymentService.processPayment(any(PaymentDTO.class))).thenThrow(new IllegalArgumentException("Invalid card number"));

        assertThrows(IllegalArgumentException.class, () -> paymentController.processPayment(invalidPaymentDTO));
    }

    @Test
    void processPaymentReturnsBadRequestForExpiredCard() {
        PaymentDTO expiredCardDTO = PaymentDTO.builder()
                .cvv("123")
                .cardNumber("1234567890123456")
                .expiry("01/20")
                .firstName("Lucas")
                .lastName("Silva")
                .build();

        when(paymentService.processPayment(any(PaymentDTO.class))).thenThrow(new IllegalArgumentException("Card expired"));

        assertThrows(IllegalArgumentException.class, () -> paymentController.processPayment(expiredCardDTO));
    }
}