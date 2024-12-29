package com.restaurant.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.entities.SubscriptionCard;
import com.restaurant.services.SubscriptionCardService;

@RestController
@RequestMapping("/api/subscription-cards")
public class SubscriptionCardController {

    @Autowired
    private SubscriptionCardService subscriptionCardService;

    // Ajouter une carte
    @PostMapping
    public ResponseEntity<SubscriptionCard> addSubscriptionCard(
            @RequestParam Long userId,
            @RequestParam double balance) {
        SubscriptionCard card = subscriptionCardService.addSubscriptionCard(userId, balance);
        return ResponseEntity.ok(card);
    }

    // Récupérer toutes les cartes
    @GetMapping
    public ResponseEntity<List<SubscriptionCard>> getAllSubscriptionCards() {
        List<SubscriptionCard> cards = subscriptionCardService.getAllSubscriptionCards();
        return ResponseEntity.ok(cards);
    }

    // Récupérer une carte par ID
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionCard> getSubscriptionCardById(@PathVariable Long id) {
        SubscriptionCard card = subscriptionCardService.getSubscriptionCardById(id);
        return ResponseEntity.ok(card);
    }

    // Mettre à jour le solde
    @PutMapping("/{id}/balance")
    public ResponseEntity<SubscriptionCard> updateBalance(
            @PathVariable Long id,
            @RequestParam double newBalance) {
        SubscriptionCard updatedCard = subscriptionCardService.updateBalance(id, newBalance);
        return ResponseEntity.ok(updatedCard);
    }

    // Bloquer une carte
    @PutMapping("/{id}/block")
    public ResponseEntity<SubscriptionCard> blockCard(@PathVariable Long id) {
        SubscriptionCard blockedCard = subscriptionCardService.blockCard(id);
        return ResponseEntity.ok(blockedCard);
    }

    // Supprimer une carte
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscriptionCard(@PathVariable Long id) {
        subscriptionCardService.deleteSubscriptionCard(id);
        return ResponseEntity.ok("Carte supprimée avec succès !");
    }
}
