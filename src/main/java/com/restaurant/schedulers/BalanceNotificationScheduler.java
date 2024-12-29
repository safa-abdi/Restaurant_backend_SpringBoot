package com.restaurant.schedulers;

import com.restaurant.services.IngredientService;
import com.restaurant.services.SubscriptionCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BalanceNotificationScheduler {

    @Autowired
    private SubscriptionCardService subscriptionCardService;

    @Autowired
    private IngredientService ingredientService;

    @Scheduled(cron = "0 32 00 * * ?")
    public void sendLowBalanceNotifications() {
        System.out.println("Scheduler exécuté à 23h56");
        subscriptionCardService.checkAndNotifyLowBalance();
    }
    @Scheduled(cron = "0 49 00 * * ?")
    public void checkAndNotifyLowIngredient() {
        System.out.println("Scheduler verifier stock ingredients exécuté à 23h56");
        ingredientService.checkAndNotifyLowIngredient();
    }
    
   
}
