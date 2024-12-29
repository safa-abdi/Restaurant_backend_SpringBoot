package com.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entities.SubscriptionCard;

public interface SubscriptionCardRepository extends JpaRepository<SubscriptionCard, Long> {
}
