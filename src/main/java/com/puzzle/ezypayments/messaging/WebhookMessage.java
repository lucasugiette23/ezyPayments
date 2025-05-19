package com.puzzle.ezypayments.messaging;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WebhookMessage implements Serializable {

    private Long paymentId;
    private String urlWebhook;

    public WebhookMessage() {}
    public WebhookMessage(Long paymentId, String urlWebhook) {
        this.paymentId = paymentId;
        this.urlWebhook = urlWebhook;
    }


}
