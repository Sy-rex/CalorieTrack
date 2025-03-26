package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealResponseDTO;
import com.sobolev.spring.calorietrack.model.Meal;

public interface MealService {
    MealResponseDTO addMeal(MealDTO mealDTO);
    MealResponseDTO getMealById(Long id);
    void deleteMeal(Long id);
}
