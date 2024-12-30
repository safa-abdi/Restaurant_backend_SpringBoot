package com.restaurant.entities;

import jakarta.persistence.*;

@Embeddable
public class DailyMeals {

    @ManyToOne
    private Meal lunch;  // Déjeuner

    @ManyToOne
    private Meal dinner;  // Dîner

    public DailyMeals() {}

    public DailyMeals(Meal lunch, Meal dinner) {
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }
}