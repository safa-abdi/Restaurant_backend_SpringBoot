package com.restaurant.services;

import com.restaurant.entities.Ingredient;
import com.restaurant.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public void updateIngredient(Long id, Ingredient ingredient) {
        if (ingredientRepository.existsById(id)) {
            // Récupérer l'ingrédient existant
            Ingredient existingIngredient = ingredientRepository.findById(id).get();

            // Mettre à jour les champs spécifiques (en évitant de réinitialiser les champs non fournis)
            if (ingredient.getName() != null) {
                existingIngredient.setName(ingredient.getName());
            }
            if (ingredient.getQuantity() != null) {
                existingIngredient.setQuantity(ingredient.getQuantity());
            }
            if (ingredient.getUnit() != null) {
                existingIngredient.setUnit(ingredient.getUnit());
            }
            if (ingredient.getPricePerUnit() != null) {
                existingIngredient.setPricePerUnit(ingredient.getPricePerUnit());
            }
            if (ingredient.getExpirationDate() != null) {
                existingIngredient.setExpirationDate(ingredient.getExpirationDate());
            }

            // Sauvegarder l'entité mise à jour
            ingredientRepository.save(existingIngredient);
        }
    }


    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
