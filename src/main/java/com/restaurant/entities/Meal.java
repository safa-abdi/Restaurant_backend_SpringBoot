package com.restaurant.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Nom du repas
    private LocalDateTime plannedDate;  // Date de planification du repas

    @ManyToMany
    private List<Ingredient> ingredients;  // Liste des ingrédients utilisés

 
    private BigDecimal cost;  // Coût total du repas

    // Constructeurs, Getters et Setters
    public Meal(String name, LocalDateTime plannedDate, List<Ingredient> ingredients, BigDecimal cost) {
        this.name = name;
        this.plannedDate = plannedDate;
        this.ingredients = ingredients;
        this.cost = cost;
    }
    // Constructeurs, Getters et Setters
    public Meal(String name, LocalDateTime plannedDate, List<Ingredient> ingredients) {
        this.name = name;
        this.plannedDate = plannedDate;
        this.ingredients = ingredients;
      
    }
    public Meal(String name, LocalDateTime plannedDate) {
        this.name = name;
        this.plannedDate = plannedDate;
      
      
    }
    public Meal() {}

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

    public LocalDateTime getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDateTime plannedDate) {
        this.plannedDate = plannedDate;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

   
}

