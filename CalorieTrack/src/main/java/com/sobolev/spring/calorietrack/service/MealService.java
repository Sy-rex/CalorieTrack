package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealResponseDTO;
import com.sobolev.spring.calorietrack.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    MealResponseDTO addMeal(MealDTO mealDTO);
    MealResponseDTO getMealById(Long id);
    void deleteMeal(Long id);
    List<Meal> findMealsByUserIdAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Meal> findAllMealsByUserId(Long userId);
}
