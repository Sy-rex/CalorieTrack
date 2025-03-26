package com.sobolev.spring.calorietrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalorieCheckDTO {

    private int totalCalories;

    private int dailyCalorieNorm;

    private boolean exceedsNorm;
}
