package com.restaurant.entities;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private SubscriptionCard subscriptionCard;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // CREDIT ou DEBIT

    private LocalDateTime transactionDate = LocalDateTime.now();

    public enum TransactionType {
        CREDIT,  // Chargement du solde
        DEBIT    // Déduction du solde
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubscriptionCard getSubscriptionCard() {
		return subscriptionCard;
	}

	public void setSubscriptionCard(SubscriptionCard subscriptionCard) {
		this.subscriptionCard = subscriptionCard;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

   
}