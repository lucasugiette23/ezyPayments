package com.puzzle.ezypayments.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "webhooks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Webhook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String endpoint;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String eventType;

}
