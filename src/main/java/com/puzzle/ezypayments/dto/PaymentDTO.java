package com.puzzle.ezypayments.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
public class PaymentDTO {
    private String firstName;
    private String lastName;
    private String expiry;
    private String cvv;
    private String cardNumber;

    private LocalDateTime createdAt;
}
