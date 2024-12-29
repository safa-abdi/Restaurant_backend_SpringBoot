package com.restaurant.entities;

import com.restaurant.repositories.MealIngredientRepository;
import com.restaurant.repositories.MealRepository;
import com.restaurant.repositories.IngredientRepository;
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

    public Meal addMeal(String name, LocalDateTime plannedDate, List<Long> ingredientIds, List<Double> quantities, BigDecimal cost) {
        // Vérification que les tailles des listes sont identiques
        if (ingredientIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Le nombre d'ingrédients et de quantités ne correspond pas.");
        }

        // Récupération des ingrédients en fonction des IDs
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        Meal meal = new Meal(name, plannedDate, ingredients, cost);

        // Sauvegarder le repas dans la base de données
        meal = mealRepository.save(meal);

        // Sauvegarder les associations entre Meal et Ingredient avec les quantités
        for (int i = 0; i < ingredientIds.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            Double quantity = quantities.get(i);

            // Créer et sauvegarder l'association MealIngredient
            MealIngredient mealIngredient = new MealIngredient(meal, ingredient, quantity);
            mealIngredientRepository.save(mealIngredient);
        }

        return meal;
    }

  
}
