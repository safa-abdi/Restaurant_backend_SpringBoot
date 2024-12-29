package com.restaurant.services;

import com.restaurant.Repositories.IngredientRepository;
import com.restaurant.Repositories.UserRepository;
import com.restaurant.entities.Ingredient;
import com.restaurant.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientService {

	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private IngredientRepository ingredientRepository;  

    private static final double LOW_ING_THRESHOLD = 10;
    BigDecimal LOW_BALANCE_THRESHOLDDec = BigDecimal.valueOf(LOW_ING_THRESHOLD);

    public void checkAndNotifyLowIngredient() {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        // Liste des ingrédients en stock faible
        StringBuilder lowStockIngredients = new StringBuilder();

        for (Ingredient ingredient : ingredients) {
            BigDecimal quantity = ingredient.getQuantity();
            if (quantity.compareTo(LOW_BALANCE_THRESHOLDDec) < 0) {
                lowStockIngredients.append("- ")
                    .append(ingredient.getName())
                    .append(": ")
                    .append(quantity.doubleValue())
                    .append(" units\n");
            }
        }

        if (lowStockIngredients.length() > 0) {
            List<User> usersAdmin = userRepository.findByRole(User.Role.ROLE_ADMIN);

            String subject = "Low Stock Ingredients Alert";
            String message = "The following ingredients have low stock:\n\n" + lowStockIngredients;

            for (User admin : usersAdmin) {
                sendEmailNotification(admin.getEmail(), subject, message);
            }
        }
    }
    private void sendEmailNotification(String adminEmail, String subject, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject(subject);
        message.setText(messageText);

        mailSender.send(message);
    }

    //------------------------------------------------------------------------------------------------------------------------
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
            Ingredient existingIngredient = ingredientRepository.findById(id).get();

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

            ingredientRepository.save(existingIngredient);
        }
    }


    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

	
}