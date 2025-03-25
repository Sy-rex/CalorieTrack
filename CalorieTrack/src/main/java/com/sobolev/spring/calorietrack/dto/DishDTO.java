package com.sobolev.spring.calorietrack.dto;

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
public class DishDTO {
    @NotBlank
    @Size(max = 200, message = "Name must be up to 200 characters long")
    private String name;

    @Positive(message = "Calories must be greater than zero")
    private Integer calories;

    @DecimalMin(value = "0.0", message = "Proteins must be non-negative")
    private Double proteins;

    @DecimalMin(value = "0.0", message = "Fats must be non-negative")
    private Double fats;

    @DecimalMin(value = "0.0", message = "Carbohydrates must be non-negative")
    private Double carbohydrates;
}
