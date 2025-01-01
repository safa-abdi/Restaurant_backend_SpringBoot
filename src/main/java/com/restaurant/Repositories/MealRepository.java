package com.restaurant.Repositories;


import com.restaurant.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findMealsByPlannedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    // Nombre de plats vendus pour une date donnée
    @Query("SELECT COUNT(m) FROM Meal m WHERE DATE(m.plannedDate) = :date")
    int countDishesSoldByDate(@Param("date") LocalDate date);

    // Nombre de plats vendus pour une période mensuelle
    @Query("SELECT COUNT(m) FROM Meal m WHERE m.plannedDate BETWEEN :startOfMonth AND :endOfMonth")
    int countDishesSoldByMonth(@Param("startOfMonth") LocalDate startOfMonth, @Param("endOfMonth") LocalDate endOfMonth);
}