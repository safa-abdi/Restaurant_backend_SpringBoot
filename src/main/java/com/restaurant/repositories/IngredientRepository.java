package com.restaurant.repositories;

import com.restaurant.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredient i WHERE i.id IN :ids")
    List<Ingredient> findAllById(@Param("ids") List<Long> ids);

}
