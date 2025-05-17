package com.puzzle.ezypayments.Entities;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
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
