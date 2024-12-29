package com.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Add the method signature here
    List<Transaction> findBySubscriptionCardId(Long subscriptionCardId);
}


