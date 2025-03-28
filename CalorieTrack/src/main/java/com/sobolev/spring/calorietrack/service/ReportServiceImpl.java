package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.*;
import com.sobolev.spring.calorietrack.model.Meal;
import com.sobolev.spring.calorietrack.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MealService mealService;
    private final UserService userService;

    @Override
    public DailyReportDTO getDailyReport(Long userId) {
        log.info("Запрос ежедневного отчёта по калориям для пользователя с ID {}", userId);

        User user = userService.getUserByIdInternal(userId);

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Meal> meals = mealService.findMealsByUserIdAndDate(userId, startOfDay, endOfDay);
        List<MealDTO> mealDTOs = meals.stream().map(this::convertToMealDTO).toList();

        log.info("Ежедневный отчёт сформирован: {} приёмов пищи", meals.size());

        return new DailyReportDTO(mealDTOs, user.getDailyCalories());
    }

    @Override
    public CalorieCheckDTO checkCalorieNorm(Long userId) {
        log.info("Проверка нормы калорий для пользователя с ID {}", userId);

        User user = userService.getUserByIdInternal(userId);

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Meal> meals = mealService.findMealsByUserIdAndDate(userId, startOfDay, endOfDay);

        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getMealDishes().stream())
                .mapToInt(mealDish -> mealDish.getDish().getCalories())
                .sum();

        boolean exceedsNorm = totalCalories > user.getDailyCalories();

        log.info("Общий потреблённый калораж: {} / {} ({} норму)",
                totalCalories,
                user.getDailyCalories(),
                exceedsNorm ? "превышает" : "не превышает");

        return new CalorieCheckDTO(totalCalories, user.getDailyCalories(), exceedsNorm);
    }

    @Override
    public MealHistoryDTO getMealHistory(Long userId) {
        log.info("Запрос истории приёмов пищи для пользователя с ID {}", userId);

        List<Meal> meals = mealService.findAllMealsByUserId(userId);
        if (meals.isEmpty()) {
            log.warn("История приёмов пищи пуста для пользователя с ID {}", userId);
        }

        List<MealDTO> mealDTOs = meals.stream().map(this::convertToMealDTO).toList();
        log.info("История приёмов пищи загружена: {} записей", meals.size());

        return new MealHistoryDTO(mealDTOs);
    }

    private MealDTO convertToMealDTO(Meal meal) {
        return new MealDTO(
                meal.getUser().getId(),
                meal.getMealTime(),
                meal.getMealType(),
                meal.getMealDishes().stream()
                        .map(mealDish -> new MealDishDTO(
                                mealDish.getDish().getId(),
                                mealDish.getPortion()
                        )).toList()
        );
    }
}
