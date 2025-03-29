package com.sobolev.spring.calorietrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для создания нового блюда")
public class DishDTO {

    @NotBlank
    @Size(max = 200, message = "Name must be up to 200 characters long")
    @Schema(description = "Название блюда", example = "Цезарь")
    private String name;

    @Positive(message = "Calories must be greater than zero")
    @Schema(description = "Калорийность блюда", example = "350")
    private Integer calories;

    @DecimalMin(value = "0.0", message = "Proteins must be non-negative")
    @Schema(description = "Количество белков (грамм)", example = "25.0")
    private Double proteins;

    @DecimalMin(value = "0.0", message = "Fats must be non-negative")
    @Schema(description = "Количество жиров (грамм)", example = "12.5")
    private Double fats;

    @DecimalMin(value = "0.0", message = "Carbohydrates must be non-negative")
    @Schema(description = "Количество углеводов (грамм)", example = "40.0")
    private Double carbohydrates;
}
