package com.restaurant.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.Repositories.MealRepository;
import com.restaurant.Repositories.PaymentRepository;
import com.restaurant.Repositories.SalesStatisticsRepository;
import com.restaurant.Repositories.TransactionRepository;
import com.restaurant.entities.SalesStatistics;

@Service
public class SalesStatisticsService {

    @Autowired
    private SalesStatisticsRepository salesStatisticsRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    
    // Calculer les statistiques quotidiennes
    public SalesStatistics calculateDailyStatistics(LocalDate date) {
        //BigDecimal totalSales = transactionRepository.sumAmountByDate(date); 
    	BigDecimal totalSales = paymentRepository.sumAmountByDate(date); 
        int totalDishesSold = paymentRepository.countDishesSoldByDate(date);     
        int totalTransactions = transactionRepository.countTransactionsByDate(date); 
        BigDecimal totalDebitTransactions = transactionRepository.sumDebitByDate(date); 
        BigDecimal totalCreditTransactions = transactionRepository.sumCreditByDate(date); 

        SalesStatistics stats = new SalesStatistics();
        stats.setDate(date);
        stats.setTotalSales(totalSales);
        stats.setTotalDishesSold(totalDishesSold);
        stats.setTotalTransactions(totalTransactions);
        stats.setTotalDebit(totalDebitTransactions);
        stats.setTotalCreadit(totalCreditTransactions);

        return salesStatisticsRepository.save(stats); // Sauvegarder les statistiques
    }
    public SalesStatistics calculateMonthlyStatistics(LocalDate startOfMonth, LocalDate endOfMonth) {
        String monthYear = startOfMonth.getYear() + "-" + startOfMonth.getMonthValue();
        LocalDateTime startOfMonthTime = startOfMonth.atStartOfDay();
        LocalDateTime endOfMonthTime = endOfMonth.atTime(23, 59, 59);

        BigDecimal totalSales = paymentRepository.sumAmountByMonth(startOfMonthTime, endOfMonthTime);
        int totalDishesSold = paymentRepository.countDishesSoldByMonth(startOfMonthTime, endOfMonthTime);
        int totalTransactions = transactionRepository.countTransactionsByMonth(startOfMonthTime, endOfMonthTime);
        BigDecimal totalDebitTransactions = transactionRepository.sumDebitByMonth(startOfMonthTime, endOfMonthTime); 
        BigDecimal totalCreditTransactions = transactionRepository.sumCreditByMonth(startOfMonthTime, endOfMonthTime); 

        SalesStatistics stats = new SalesStatistics();
        stats.setMonthYear(monthYear);
        stats.setTotalSales(totalSales);
        stats.setTotalDishesSold(totalDishesSold);
        stats.setTotalTransactions(totalTransactions);
        stats.setTotalDebit(totalDebitTransactions);
        stats.setTotalCreadit(totalCreditTransactions);
        return salesStatisticsRepository.save(stats);
    }


}
