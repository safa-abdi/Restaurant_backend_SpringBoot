package com.restaurant.controllers;

import com.restaurant.entities.Meal;
import com.restaurant.entities.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping
    public ResponseEntity<Meal> addMeal(@RequestParam String name,
                                        @RequestParam String plannedDate, 
                                        @RequestParam List<Long> ingredientIds,
                                        @RequestParam List<Double> quantities, // Quantités ajoutées ici
                                        @RequestParam BigDecimal cost) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(plannedDate, formatter);
            Meal meal = mealService.addMeal(name, dateTime, ingredientIds, quantities, cost);
            return ResponseEntity.status(HttpStatus.CREATED).body(meal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Handle invalid date format
        }
    }

   
}
