package com.sobolev.spring.calorietrack.dto;

import com.sobolev.spring.calorietrack.model.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealResponseDTO {
    private Long id;
    private Long userId;
    private LocalDateTime mealTime;
    private MealType mealType;
    private List<MealDishDTO> mealDishes;
}
