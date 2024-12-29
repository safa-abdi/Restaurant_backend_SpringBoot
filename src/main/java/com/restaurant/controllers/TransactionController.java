package com.restaurant.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.entities.Transaction;
import com.restaurant.services.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/charge")
    public ResponseEntity<Transaction> chargeCard(@RequestParam Long cardId, @RequestParam double amount) {
        Transaction transaction = transactionService.performTransaction(cardId, amount, Transaction.TransactionType.CREDIT);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/deduct")
    public ResponseEntity<Transaction> deductFromCard(@RequestParam Long cardId, @RequestParam double amount) {
        Transaction transaction = transactionService.performTransaction(cardId, amount, Transaction.TransactionType.DEBIT);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long cardId) {
        List<Transaction> transactions = transactionService.getTransactionsByCardId(cardId);
        return ResponseEntity.ok(transactions);
    }
}