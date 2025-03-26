package com.sobolev.spring.calorietrack.repository;

import com.sobolev.spring.calorietrack.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findById(Long id);

    List<Meal> findByUserIdAndMealTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);

    List<Meal> findByUserIdAndMealTimeAfter(Long userId, LocalDateTime start);

    List<Meal> findByUserId(Long userId);
}
