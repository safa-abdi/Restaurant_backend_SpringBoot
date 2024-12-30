package com.restaurant.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.Repositories.IngredientRepository;
import com.restaurant.Repositories.MealIngredientRepository;
import com.restaurant.Repositories.MealRepository;
import com.restaurant.entities.Ingredient;
import com.restaurant.entities.Meal;
import com.restaurant.entities.MealIngredient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MealIngredientRepository mealIngredientRepository;

    public Meal addMeal(String name, LocalDateTime plannedDate, List<Long> ingredientIds, List<BigDecimal> quantities, BigDecimal cost) {
        if (ingredientIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Le nombre d'ingrédients et de quantités ne correspond pas.");
        }

        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        Meal meal = new Meal(name, plannedDate, ingredients, cost);
        meal = mealRepository.save(meal);

        for (int i = 0; i < ingredientIds.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            BigDecimal quantityUsed = quantities.get(i);

            MealIngredient mealIngredient = new MealIngredient(meal, ingredient, quantityUsed);
            mealIngredientRepository.save(mealIngredient);

            updateIngredientStock(ingredient, quantityUsed);
        }

        return meal;
    }
    public Meal findMealById(Long id) {
        Optional<Meal> meal = mealRepository.findById(id);
        if (meal.isPresent()) {
            return meal.get();
        } else {
            throw new IllegalArgumentException("Meal with ID " + id + " not found");
        }
    }

    public Meal saveMeal(Meal meal) {
        return mealRepository.save(meal);
    }
    private void updateIngredientStock(Ingredient ingredient, BigDecimal quantityUsed) {
        if (ingredient.getQuantity().compareTo(quantityUsed) >= 0) {
            ingredient.setQuantity(ingredient.getQuantity().subtract(quantityUsed));
            ingredientRepository.save(ingredient);
        } else {
            throw new IllegalArgumentException("Quantité d'ingrédient insuffisante.");
        }
    }

    public List<Meal> getMealsByWeek(int weekNumber) {
        return null;
    }
}