package com.sobolev.spring.calorietrack.dto;

import com.sobolev.spring.calorietrack.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealHistoryDTO {
    private List<MealDTO> meals;
}
