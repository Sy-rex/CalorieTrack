package com.sobolev.spring.calorietrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для проверки калорийности")
public class CalorieCheckDTO {

    @Schema(description = "Общее количество потребленных калорий", example = "2200")
    private int totalCalories;

    @Schema(description = "Ежедневная норма калорий", example = "2000")
    private int dailyCalorieNorm;

    @Schema(description = "Превышена ли дневная норма калорий", example = "true")
    private boolean exceedsNorm;
}
