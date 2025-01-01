package com.restaurant.Repositories;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restaurant.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
 // Somme des montants des transactions pour une date donnée
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE DATE(t.transactionDate) = :date")
    BigDecimal sumAmountByDate(@Param("date") LocalDate date);

    // Nombre de transactions pour une date donnée
    @Query("SELECT COUNT(t) FROM Transaction t WHERE DATE(t.transactionDate) = :date")
    int countTransactionsByDate(@Param("date") LocalDate date);

    // Somme des montants des transactions pour une période mensuelle
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionDate BETWEEN :startOfMonth AND :endOfMonth")
    BigDecimal sumAmountByMonth(@Param("startOfMonth") LocalDateTime startOfMonthTime, @Param("endOfMonth") LocalDateTime endOfMonthTime);

    // Nombre de transactions pour une période mensuelle
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionDate BETWEEN :startOfMonth AND :endOfMonth")
    int countTransactionsByMonth(@Param("startOfMonth") LocalDateTime startOfMonthTime, @Param("endOfMonth") LocalDateTime endOfMonthTime);

	List<Transaction> findBySubscriptionCardId(Long cardId);
	
	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE DATE(t.transactionDate) = :date AND type = 'DEBIT'")
	BigDecimal sumDebitByDate(@Param("date") LocalDate date);

	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE DATE(t.transactionDate) = :date AND type = 'CREDIT'")
	BigDecimal sumCreditByDate(@Param("date") LocalDate date);


    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionDate BETWEEN :startOfMonth AND :endOfMonth   AND type = 'DEBIT' ")
    BigDecimal sumDebitByMonth(@Param("startOfMonth") LocalDateTime startOfMonthTime, @Param("endOfMonth") LocalDateTime endOfMonthTime);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionDate BETWEEN :startOfMonth AND :endOfMonth AND type = 'CREDIT'")
    BigDecimal sumCreditByMonth(@Param("startOfMonth") LocalDateTime startOfMonthTime, @Param("endOfMonth") LocalDateTime endOfMonthTime);

}