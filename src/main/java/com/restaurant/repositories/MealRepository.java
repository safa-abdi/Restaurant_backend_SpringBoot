package com.restaurant.repositories;

import com.restaurant.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findMealsByPlannedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
