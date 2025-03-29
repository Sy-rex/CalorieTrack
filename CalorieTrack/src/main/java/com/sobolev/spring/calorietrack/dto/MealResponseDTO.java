package com.sobolev.spring.calorietrack.dto;

import com.sobolev.spring.calorietrack.model.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для отображения информации о приёме пищи")
public class MealResponseDTO {

    @Schema(description = "ID приёма пищи", example = "10")
    private Long id;

    @Schema(description = "ID пользователя", example = "1001")
    private Long userId;

    @Schema(description = "Дата и время приёма пищи", example = "2024-03-29T12:30:00")
    private LocalDateTime mealTime;

    @Schema(description = "Тип приёма пищи", example = "DINNER")
    private MealType mealType;

    @Schema(description = "Список блюд в приёме пищи")
    private List<MealDishDTO> mealDishes;
}
