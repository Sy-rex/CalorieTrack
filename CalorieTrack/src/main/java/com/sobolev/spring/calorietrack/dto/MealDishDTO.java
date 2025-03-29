package com.sobolev.spring.calorietrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для добавления блюда в приём пищи")
public class MealDishDTO {

    @NotNull
    @Schema(description = "ID блюда", example = "1")
    private Long dishId;

    @Positive
    @Schema(description = "Количество порций", example = "2")
    private int portion;
}
