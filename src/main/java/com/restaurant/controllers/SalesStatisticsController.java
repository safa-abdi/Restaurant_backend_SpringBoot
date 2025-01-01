package com.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.Repositories.PaymentRepository;
import com.restaurant.entities.SalesStatistics;
import com.restaurant.services.SalesStatisticsService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/stats")
public class SalesStatisticsController {

    @Autowired
    private SalesStatisticsService salesStatisticsService;

    // Endpoint pour calculer et récupérer les statistiques quotidiennes
    //@GetMapping("/daily")
  //  public ResponseEntity<SalesStatistics> getDailyStatistics(@RequestParam("date") String date) {
      //  LocalDate localDate = LocalDate.parse(date);
       // SalesStatistics dailyStats = salesStatisticsService.calculateDailyStatistics(localDate);
        //return ResponseEntity.ok(dailyStats);
    //}
    
    @GetMapping("/daily")
    public ResponseEntity<SalesStatistics> getDailyStatistics(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        SalesStatistics dailyStats = salesStatisticsService.calculateDailyStatistics(localDate);
        return ResponseEntity.ok(dailyStats);
    }

    // Endpoint pour calculer et récupérer les statistiques mensuelles
    @GetMapping("/monthly")
    public ResponseEntity<SalesStatistics> getMonthlyStatistics(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
        
        LocalDate startOfMonth = LocalDate.parse(startDate);
        LocalDate endOfMonth = LocalDate.parse(endDate);
        SalesStatistics monthlyStats = salesStatisticsService.calculateMonthlyStatistics(startOfMonth, endOfMonth);
        return ResponseEntity.ok(monthlyStats);
    }
}
