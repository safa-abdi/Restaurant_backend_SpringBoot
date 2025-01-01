package com.restaurant.Repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.entities.SalesStatistics;

@Repository
public interface SalesStatisticsRepository extends JpaRepository<SalesStatistics, Long> {

    Optional<SalesStatistics> findByDate(LocalDate date);

    Optional<SalesStatistics> findByMonthYear(String monthYear);

}
