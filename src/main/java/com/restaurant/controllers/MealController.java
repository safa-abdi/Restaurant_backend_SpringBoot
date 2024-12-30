package com.restaurant.controllers;

import com.restaurant.entities.Meal;
import com.restaurant.services.MealService;

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
                                        @RequestParam List<BigDecimal> quantities,  // Changed to BigDecimal
                                        @RequestParam BigDecimal cost) {
        try {
            // Parse the plannedDate string to LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(plannedDate, formatter);

            // Call the service to add the meal
            Meal meal = mealService.addMeal(name, dateTime, ingredientIds, quantities, cost);

            // Return created meal as response
            return ResponseEntity.status(HttpStatus.CREATED).body(meal);
        } catch (Exception e) {
            // Log the error (optional) and return a bad request response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // You can add an error message if desired
        }
    }

    @GetMapping("/week/{weekNumber}")
    public ResponseEntity<List<Meal>> getMealsByWeek(@PathVariable int weekNumber) {
        try {
            // Retrieve meals by week
            List<Meal> meals = mealService.getMealsByWeek(weekNumber);

            // Return the list of meals
            return ResponseEntity.ok(meals);
        } catch (Exception e) {
            // Log the error (optional) and return an internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // You can add an error message if needed
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Meal> selectMealById(@PathVariable Long id) {
        try {
            Meal meal = mealService.getMealById(id);
            if (meal != null) {
                return ResponseEntity.ok(meal);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Meal>> selectAllMeals() {
        try {
            List<Meal> meals = mealService.getAllMeals();
            return ResponseEntity.ok(meals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}