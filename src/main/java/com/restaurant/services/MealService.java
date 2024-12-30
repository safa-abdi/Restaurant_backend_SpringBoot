package com.restaurant.services;

import com.restaurant.Repositories.MealRepository;
import com.restaurant.entities.Ingredient;
import com.restaurant.entities.Meal;
import com.restaurant.entities.MealIngredient;
import com.restaurant.Repositories.IngredientRepository;
import com.restaurant.Repositories.MealIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MealIngredientRepository mealIngredientRepository;

    public Meal addMeal(String name, LocalDateTime plannedDate, List<Long> ingredientIds, List<BigDecimal> quantities, BigDecimal cost) {
        // Check if ingredientIds and quantities have the same size
        if (ingredientIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Le nombre d'ingrédients et de quantités ne correspond pas.");
        }

        // Retrieve the ingredients from the database
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        Meal meal = new Meal(name, plannedDate, ingredients, cost);
        meal = mealRepository.save(meal);

        // Process each ingredient and quantity
        for (int i = 0; i < ingredientIds.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            BigDecimal quantityUsed = quantities.get(i);

            // Save the association between Meal and Ingredient
            MealIngredient mealIngredient = new MealIngredient(meal, ingredient, quantityUsed);
            mealIngredientRepository.save(mealIngredient);

            // Update the stock of the ingredient
            updateIngredientStock(ingredient, quantityUsed);
        }

        return meal;
    }

    // Method to update the ingredient stock
    private void updateIngredientStock(Ingredient ingredient, BigDecimal quantityUsed) {
        // Check if there is enough stock
        if (ingredient.getQuantity().compareTo(quantityUsed) >= 0) {
            // Subtract the quantity used from the ingredient's stock
            ingredient.setQuantity(ingredient.getQuantity().subtract(quantityUsed));
            ingredientRepository.save(ingredient);
        } else {
            throw new IllegalArgumentException("Quantité d'ingrédient insuffisante.");
        }
    }

    public List<Meal> getMealsByWeek(int weekNumber) {
        // Placeholder for future implementation of filtering by week
        return null;
    }
    public Meal getMealById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

}