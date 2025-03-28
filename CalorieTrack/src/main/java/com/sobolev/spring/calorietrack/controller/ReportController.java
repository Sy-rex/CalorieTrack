package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.CalorieCheckDTO;
import com.sobolev.spring.calorietrack.dto.DailyReportDTO;
import com.sobolev.spring.calorietrack.dto.MealHistoryDTO;
import com.sobolev.spring.calorietrack.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/daily")
    public DailyReportDTO getDailyReport(@RequestParam Long userId) {
        log.info("GET /api/reports - Получение дневного отчёта");
        return reportService.getDailyReport(userId);
    }

    @GetMapping("/calorie-check")
    public CalorieCheckDTO checkCalorieNorm(@RequestParam Long userId) {
        log.info("GET /api/reports - Проверка нормы калорий");
        return reportService.checkCalorieNorm(userId);
    }

    @GetMapping("/history")
    public MealHistoryDTO getMealHistory(@RequestParam Long userId) {
        log.info("GET /api/reports - Получение всего питания");
        return reportService.getMealHistory(userId);
    }
}
