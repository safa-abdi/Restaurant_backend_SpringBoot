package com.restaurant.controllers;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.text.DocumentException;
import com.restaurant.entities.Payment;
import com.restaurant.services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam Long userId, 
            @RequestParam Long mealId, 
            @RequestParam String paymentMethod, 
            @RequestParam(required = false) String status) { 

        status = "incompleted";
        Payment savedPayment = paymentService.createPayment(userId, mealId, paymentMethod, status);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setId(id);
        Payment updatedPayment = paymentService.updatePayment(payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/valider_paiement/{id}")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id)
            .orElseGet(() -> null); 
        
        if (payment == null) {
            return ResponseEntity.notFound().build(); 
        }

        payment.setStatus("Completed"); 
        Payment updatedPayment = paymentService.updatePayment(payment); 

        return ResponseEntity.ok(updatedPayment);
    }
    
    // Point d'API pour générer un reçu
    @GetMapping("/receipt/{paymentId}")
    public String generateReceipt(@PathVariable Long paymentId) throws java.io.IOException {
        Payment payment = paymentService.getPaymentById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        try {
            return paymentService.generateReceipt(payment);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return "Error generating receipt: " + e.getMessage();
        }
    }
}
