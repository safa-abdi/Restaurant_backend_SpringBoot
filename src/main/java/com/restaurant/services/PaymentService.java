package com.restaurant.services;

import com.restaurant.entities.Meal;
import com.restaurant.entities.Payment;
import com.restaurant.entities.User;
import com.restaurant.Repositories.PaymentRepository;
import com.restaurant.Repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MealRepository mealRepository; 

    @Autowired
    private UserService userService; 

    public Payment createPayment(Long userId, Long mealId, String paymentMethod, String status) {
        Optional<User> optionalUser = Optional.ofNullable(userService.getUserById(userId));
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        Optional<Meal> optionalMeal = mealRepository.findById(mealId);
        if (!optionalMeal.isPresent()) {
            throw new RuntimeException("Meal not found");
        }

        Meal meal = optionalMeal.get();

        BigDecimal amount = meal.getCost();

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(status);
        payment.setMeal(meal);  
        payment.setUser(user);  

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
    public String generateReceipt(Payment payment) throws DocumentException, IOException {
        Document document = new Document();
        String fileName = "receipt_" + payment.getId() + ".pdf";
        
        // Chemin où le fichier PDF sera sauvegardé
        String path = "C:/Users/Lenovo/Desktop/recu paiement spring/" + fileName;
        
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        // Ajout de contenu au PDF
        document.add(new Paragraph("Payment Receipt"));
        document.add(new Paragraph("ID: " + payment.getId()));
        document.add(new Paragraph("Amount: " + payment.getAmount()));
        document.add(new Paragraph("Payment Method: " + payment.getPaymentMethod()));
        document.add(new Paragraph("Status: " + payment.getStatus()));
        document.add(new Paragraph("Meal: " + payment.getMeal().getName()));
        document.add(new Paragraph("User: " + payment.getUser().getFirstname()));
        document.add(new Paragraph("Date of Payment: " + payment.getCreatedAt()));

        document.close();

        return "Receipt generated at: " + path;
    }
}
