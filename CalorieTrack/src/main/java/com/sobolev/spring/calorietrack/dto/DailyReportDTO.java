package com.sobolev.spring.calorietrack.dto;

import com.sobolev.spring.calorietrack.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportDTO {

    private List<MealDTO> meals;

    private int dailyCalorieNorm;
}
