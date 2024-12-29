package com.restaurant.Repositories;


import com.restaurant.entities.SubscriptionCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionCardRepository extends JpaRepository<SubscriptionCard, Long> {
}