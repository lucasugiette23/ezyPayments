package com.puzzle.ezypayments.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Builder
@Data
@Getter
@Setter
public class PaymentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
}
