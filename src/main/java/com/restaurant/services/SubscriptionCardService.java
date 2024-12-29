package com.restaurant.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.entities.SubscriptionCard;
import com.restaurant.entities.Transaction;
import com.restaurant.entities.User;
import com.restaurant.repositories.SubscriptionCardRepository;
import com.restaurant.repositories.TransactionRepository;
import com.restaurant.repositories.UserRepository;

@Service
public class SubscriptionCardService {

    @Autowired
    private SubscriptionCardRepository subscriptionCardRepository;

    @Autowired
    private UserRepository userRepository;

    // Ajouter une nouvelle carte
    public SubscriptionCard addSubscriptionCard(Long userId, double balance) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Utilisateur non trouvé avec l'id : " + userId);
        }

        User user = userOptional.get();
        SubscriptionCard card = new SubscriptionCard(user, balance, SubscriptionCard.Status.ACTIVE);
        return subscriptionCardRepository.save(card);
    }

    // Récupérer toutes les cartes
    public List<SubscriptionCard> getAllSubscriptionCards() {
        return subscriptionCardRepository.findAll();
    }

    // Récupérer une carte par ID
    public SubscriptionCard getSubscriptionCardById(Long id) {
        return subscriptionCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée avec l'id : " + id));
    }

    // Mettre à jour le solde de la carte
    public SubscriptionCard updateBalance(Long cardId, double newBalance) {
        SubscriptionCard card = getSubscriptionCardById(cardId);
        card.setBalance(newBalance);
        return subscriptionCardRepository.save(card);
    }

    // Bloquer une carte
    public SubscriptionCard blockCard(Long cardId) {
        SubscriptionCard card = getSubscriptionCardById(cardId);
        card.setStatus(SubscriptionCard.Status.BLOCKED);
        return subscriptionCardRepository.save(card);
    }

    // Supprimer une carte
    public void deleteSubscriptionCard(Long id) {
        subscriptionCardRepository.deleteById(id);
    }
    public SubscriptionCard changeCardStatus(Long cardId, SubscriptionCard.Status newStatus) {
        // Vérifier si la carte existe
        SubscriptionCard card = subscriptionCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Carte introuvable avec l'ID : " + cardId));

        // Modifier le statut
        card.setStatus(newStatus);
        return subscriptionCardRepository.save(card);
    }
    @Autowired
    private TransactionRepository transactionRepository;
    // Recharge balance and create a transaction
    public SubscriptionCard rechargeBalance(Long cardId, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Le montant doit être supérieur à zéro.");
        }

        SubscriptionCard card = getSubscriptionCardById(cardId);
        card.setBalance(card.getBalance() + amount);

        // Create a transaction for the recharge
        Transaction transaction = new Transaction();
        transaction.setSubscriptionCard(card);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.CREDIT);  // Mark as CREDIT for recharge
        transactionRepository.save(transaction);  // Save the transaction

        return subscriptionCardRepository.save(card);
    }
    
    
    //débit
    public SubscriptionCard debitBalance(Long cardId, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Le montant doit être supérieur à zéro.");
        }

        // Récupérer la carte par ID
        SubscriptionCard card = getSubscriptionCardById(cardId);

        // Vérifier si le solde est suffisant
        if (card.getBalance() < amount) {
            throw new RuntimeException("Solde insuffisant pour effectuer cette opération.");
        }

        // Débiter le montant du solde
        card.setBalance(card.getBalance() - amount);

        // Créer une transaction pour le débit
        Transaction transaction = new Transaction();
        transaction.setSubscriptionCard(card);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.DEBIT);  // Marquer comme DEBIT pour le retrait
        transactionRepository.save(transaction);  // Enregistrer la transaction

        // Sauvegarder la carte mise à jour
        return subscriptionCardRepository.save(card);
    }
}
