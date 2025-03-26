package com.sobolev.spring.calorietrack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sobolev.spring.calorietrack.model.MealType;
import com.sobolev.spring.calorietrack.util.GoalDeserializer;
import com.sobolev.spring.calorietrack.util.MealDeserializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {
    @NotNull
    private Long userId;

    @NotNull
    private LocalDateTime mealTime;

    @NotNull(message = "Meal type cannot be null")
    @JsonDeserialize(using = MealDeserializer.class)
    private MealType mealType;

    @NotEmpty(message = "Meal must contain at least one dish")
    private List<MealDishDTO> mealDishes;
}
