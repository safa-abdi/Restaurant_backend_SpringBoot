package com.restaurant.Repositories;


import com.restaurant.entities.Menu;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByStartDate(LocalDate startDate);
}