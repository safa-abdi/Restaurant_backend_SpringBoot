package com.restaurant.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "subscription_cards") // Nom de la table
public class SubscriptionCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // FK vers l'utilisateur
    private User user;

    private double balance; // Solde de la carte

    private Status status; // État de la carte (Active ou Bloquée)

    @CreationTimestamp
    private LocalDateTime createdAt; // Date de création

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Dernière mise à jour
    @OneToMany(mappedBy = "subscriptionCard")
    private List<Transaction> transactions;

    public enum Status {
        ACTIVE,
        BLOCKED
    }

    // Constructeurs
    public SubscriptionCard() {
    }

    public SubscriptionCard(User user, double balance, Status status) {
        this.user = user;
        this.balance = balance;
        this.status = status;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "SubscriptionCard [id=" + id + ", user=" + user.getId() + ", balance=" + balance + ", status=" + status
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
