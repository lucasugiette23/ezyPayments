package com.puzzle.ezypayments.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebhookDTO {

    @Column(nullable = false)
    private String endpoint;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String eventType;
}
