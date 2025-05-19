package com.puzzle.ezypayments.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Webhook {

    @Id
    @GeneratedValue
    private Long id;

    private String endpoint;

}
