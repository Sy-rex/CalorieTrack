package com.sobolev.spring.calorietrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для дневного отчета")
public class DailyReportDTO {

    @Schema(description = "Список приемов пищи в течение дня")
    private List<MealDTO> meals;

    @Schema(description = "Ежедневная норма калорий", example = "2000")
    private int dailyCalorieNorm;
}
