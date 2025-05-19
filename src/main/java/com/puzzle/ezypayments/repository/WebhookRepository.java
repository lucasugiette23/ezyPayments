package com.puzzle.ezypayments.repository;

import com.puzzle.ezypayments.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, Long> {

    List<Webhook> findAllByClientId(String clientId);

}
