package com.restaurant.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Nom de l'ingrédient
    private BigDecimal quantity;  // Quantité disponible
    private String unit;  // Unité de mesure (kg, litre, etc.)
    private BigDecimal pricePerUnit;  // Prix par unité
    private LocalDateTime expirationDate;  // Date d'expiration de l'ingrédient
    @CreationTimestamp
    private LocalDateTime createdAt; // Date de création

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Dernière mise à jour

    // Constructeurs
    public Ingredient(String name, BigDecimal quantity, String unit, BigDecimal pricePerUnit, LocalDateTime expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.expirationDate = expirationDate;
    }

    public Ingredient() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Ingredient [id=" + id + ", name=" + name + ", quantity=" + quantity + ", unit=" + unit + ", pricePerUnit=" + pricePerUnit + ", expirationDate=" + expirationDate + "]";
    }
}
