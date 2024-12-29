
package com.restaurant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.Repositories.SubscriptionCardRepository;
import com.restaurant.Repositories.TransactionRepository;
import com.restaurant.entities.SubscriptionCard;
import com.restaurant.entities.Transaction;

@Service
public class TransactionService {

    @Autowired
    private SubscriptionCardRepository subscriptionCardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction performTransaction(Long cardId, double amount, Transaction.TransactionType type) {
        // Vérifier si la carte existe
        SubscriptionCard card = subscriptionCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Carte introuvable avec l'ID : " + cardId));

        // Mettre à jour le solde en fonction du type de transaction
        if (type == Transaction.TransactionType.DEBIT) {
            if (card.getBalance() < amount) {
                throw new RuntimeException("Solde insuffisant pour effectuer cette transaction !");
            }
            card.setBalance(card.getBalance() - amount);
        } else if (type == Transaction.TransactionType.CREDIT) {
            card.setBalance(card.getBalance() + amount);
        }

        // Enregistrer les modifications de la carte
        subscriptionCardRepository.save(card);

        // Enregistrer la transaction
        Transaction transaction = new Transaction();
        transaction.setSubscriptionCard(card);
        transaction.setAmount(amount);
        transaction.setType(type);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByCardId(Long cardId) {
        return transactionRepository.findBySubscriptionCardId(cardId);
    }
}
