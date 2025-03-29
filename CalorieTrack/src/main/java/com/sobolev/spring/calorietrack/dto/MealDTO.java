package com.sobolev.spring.calorietrack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sobolev.spring.calorietrack.model.MealType;
import com.sobolev.spring.calorietrack.util.MealDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO для создания нового приёма пищи")
public class MealDTO {

    @NotNull
    @Schema(description = "ID пользователя", example = "1001")
    private Long userId;

    @NotNull
    @Schema(description = "Дата и время приёма пищи", example = "2024-03-29T12:30:00")
    private LocalDateTime mealTime;

    @NotNull(message = "Meal type cannot be null")
    @JsonDeserialize(using = MealDeserializer.class)
    @Schema(description = "Тип приёма пищи", example = "BREAKFAST")
    private MealType mealType;

    @NotEmpty(message = "Meal must contain at least one dish")
    @Schema(description = "Список блюд в приёме пищи")
    private List<MealDishDTO> mealDishes;
}
