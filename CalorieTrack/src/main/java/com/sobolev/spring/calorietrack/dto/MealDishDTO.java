package com.sobolev.spring.calorietrack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDishDTO {
    @NotNull
    private Long dishId;

    @Positive
    private int portion;
}
