package com.restaurant.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_statistics")
public class SalesStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;               
    private String monthYear;             
    private BigDecimal totalSales;        
    private int totalDishesSold;         
    private int totalTransactions;        
    private BigDecimal totalDebit;        
    private BigDecimal totalCreadit;        

    
  

	@Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;


    
    
    public SalesStatistics() {
        this.createdAt = LocalDateTime.now();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public int getTotalDishesSold() {
		return totalDishesSold;
	}

	public void setTotalDishesSold(int totalDishesSold) {
		this.totalDishesSold = totalDishesSold;
	}

	public int getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public BigDecimal getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}
	public BigDecimal getTotalCreadit() {
		return totalCreadit;
	}
	public void setTotalCreadit(BigDecimal totalCreadit) {
		this.totalCreadit = totalCreadit;
	}

	

}
