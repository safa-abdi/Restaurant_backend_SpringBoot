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
    
    
    @PutMapping("/{id}/unblock")
    public ResponseEntity<SubscriptionCard> unblockCard(@PathVariable Long id) {
        SubscriptionCard unblockedCard = subscriptionCardService.changeCardStatus(id, SubscriptionCard.Status.ACTIVE);
        return ResponseEntity.ok(unblockedCard);
    }

    // Supprimer une carte
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscriptionCard(@PathVariable Long id) {
        subscriptionCardService.deleteSubscriptionCard(id);
        return ResponseEntity.ok("Carte supprimée avec succès !");
    }
    
    // Recharge card (now also logs a transaction)
    @PutMapping("/{id}/recharge")
    public ResponseEntity<SubscriptionCard> rechargeBalance(
            @PathVariable Long id,
            @RequestParam double amount) {
        SubscriptionCard rechargedCard = subscriptionCardService.rechargeBalance(id, amount);
        return ResponseEntity.ok(rechargedCard);
    }
 // Débiter un montant de la carte
    @PutMapping("/{id}/debit")
    public ResponseEntity<SubscriptionCard> debitBalance(
            @PathVariable Long id,
            @RequestParam double amount) {
        SubscriptionCard debitedCard = subscriptionCardService.debitBalance(id, amount);
        return ResponseEntity.ok(debitedCard);
    }

    
}
