package com.restaurant.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.entities.SubscriptionCard;
import com.restaurant.entities.User;
import com.restaurant.repositories.SubscriptionCardRepository;
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
}
