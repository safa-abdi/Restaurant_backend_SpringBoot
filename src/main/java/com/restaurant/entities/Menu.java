package com.restaurant.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;  // Date de début de la semaine

    @ElementCollection
    @CollectionTable(name = "menu_meals", joinColumns = @JoinColumn(name = "menu_id"))
    @MapKeyColumn(name = "day")
    @Column(name = "meals")
    private Map<String, DailyMeals> dailyMeals;  // Repas pour chaque jour de la semaine

    public Menu() {}

    public Menu(LocalDate startDate, Map<String, DailyMeals> dailyMeals) {
        this.startDate = startDate;
        this.dailyMeals = dailyMeals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Map<String, DailyMeals> getDailyMeals() {
        return dailyMeals;
    }

    public void setDailyMeals(Map<String, DailyMeals> dailyMeals) {
        this.dailyMeals = dailyMeals;
    }
}