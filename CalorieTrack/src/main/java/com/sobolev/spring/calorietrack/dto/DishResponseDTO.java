package com.sobolev.spring.calorietrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishResponseDTO {
    private Long id;
    private String name;
    private Integer calories;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
}
