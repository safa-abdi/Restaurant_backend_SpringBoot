package com.restaurant.Repositories;

import com.restaurant.entities.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	
	@Query("SELECT SUM(t.amount) FROM Payment t WHERE CAST(t.createdAt AS DATE) = :date AND status = 'Completed'  ")
	BigDecimal sumAmountByDate(@Param("date") LocalDate date);

	  @Query("SELECT COUNT(p) FROM Payment p WHERE CAST(p.createdAt AS DATE) = :date AND status = 'Completed'   ")
    int countDishesSoldByDate(@Param("date") LocalDate date);
	  
	  @Query("SELECT COUNT(m) FROM Payment m WHERE m.createdAt BETWEEN :startOfMonth AND :endOfMonth AND status = 'Completed'  " )
	  int countDishesSoldByMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

	  @Query("SELECT SUM(m.amount) FROM  Payment m WHERE m.createdAt BETWEEN :startOfMonth AND :endOfMonth AND status = 'Completed'  ")
	  BigDecimal sumAmountByMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);


}
