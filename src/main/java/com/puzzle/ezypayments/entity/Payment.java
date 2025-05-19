package com.puzzle.ezypayments.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String expiry;
    private String cvv;
    private String encryptedCardNumber;

    private LocalDateTime createdAt;
}
