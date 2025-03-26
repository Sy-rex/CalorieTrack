package com.sobolev.spring.calorietrack.repository;

import com.sobolev.spring.calorietrack.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    boolean existsByName(String name);

    Optional<Dish> findById(Long id);
}
