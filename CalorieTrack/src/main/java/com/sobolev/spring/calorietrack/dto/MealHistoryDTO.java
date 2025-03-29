package com.sobolev.spring.calorietrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для отображения истории приёмов пищи")
public class MealHistoryDTO {

    @Schema(description = "Список приёмов пищи")
    private List<MealDTO> meals;
}
