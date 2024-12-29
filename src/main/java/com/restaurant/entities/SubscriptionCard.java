package com.restaurant.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "subscription_cards")
public class SubscriptionCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) 
    private User user;

    private double balance; 

    private Status status; 

    @CreationTimestamp
    private LocalDateTime createdAt; 

    @UpdateTimestamp
    private LocalDateTime updatedAt; 

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