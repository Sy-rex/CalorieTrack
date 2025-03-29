package com.sobolev.spring.calorietrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для отображения информации о блюде")
public class DishResponseDTO {

    @Schema(description = "Идентификатор блюда", example = "1")
    private Long id;

    @Schema(description = "Название блюда", example = "Цезарь")
    private String name;

    @Schema(description = "Калорийность блюда", example = "350")
    private Integer calories;

    @Schema(description = "Количество белков (грамм)", example = "25.0")
    private Double proteins;

    @Schema(description = "Количество жиров (грамм)", example = "12.5")
    private Double fats;

    @Schema(description = "Количество углеводов (грамм)", example = "40.0")
    private Double carbohydrates;
}
