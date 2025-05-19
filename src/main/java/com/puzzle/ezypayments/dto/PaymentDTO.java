package com.puzzle.ezypayments.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class PaymentDTO {
    private String firstName;
    private String lastName;
    private String expiry;
    private String cvv;
    private String cardNumber;

    private LocalDateTime createdAt;
}
