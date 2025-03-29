package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.CalorieCheckDTO;
import com.sobolev.spring.calorietrack.dto.DailyReportDTO;
import com.sobolev.spring.calorietrack.dto.MealHistoryDTO;
import com.sobolev.spring.calorietrack.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Report Controller", description = "Генерация отчетов по питанию пользователей")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Получение дневного отчёта", description = "Возвращает отчет о потребленных калориях и блюдах за день.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Отчёт успешно получен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации параметров"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/daily")
    public ResponseEntity<DailyReportDTO> getDailyReport(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @RequestParam Long userId
    ) {
        log.info("GET /api/reports/daily - Получение дневного отчёта для пользователя {}", userId);
        return ResponseEntity.ok(reportService.getDailyReport(userId));
    }

    @Operation(summary = "Проверка нормы калорий", description = "Определяет, превышен ли суточный лимит калорий пользователем.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Проверка успешно выполнена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации параметров"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/calorie-check")
    public ResponseEntity<CalorieCheckDTO> checkCalorieNorm(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @RequestParam Long userId
    ) {
        log.info("GET /api/reports/calorie-check - Проверка нормы калорий для пользователя {}", userId);
        return ResponseEntity.ok(reportService.checkCalorieNorm(userId));
    }

    @Operation(summary = "Получение истории питания", description = "Возвращает историю всех приёмов пищи пользователя.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "История питания успешно получена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации параметров"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/history")
    public ResponseEntity<MealHistoryDTO> getMealHistory(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @RequestParam Long userId
    ) {
        log.info("GET /api/reports/history - Получение истории питания пользователя {}", userId);
        return ResponseEntity.ok(reportService.getMealHistory(userId));
    }
}
