package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.CalorieCheckDTO;
import com.sobolev.spring.calorietrack.dto.DailyReportDTO;
import com.sobolev.spring.calorietrack.dto.MealHistoryDTO;

public interface ReportService {
    DailyReportDTO getDailyReport(Long userId);

    CalorieCheckDTO checkCalorieNorm(Long userId);

    MealHistoryDTO getMealHistory(Long userId);
}
