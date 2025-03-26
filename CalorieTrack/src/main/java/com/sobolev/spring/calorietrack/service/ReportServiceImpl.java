package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.*;
import com.sobolev.spring.calorietrack.model.Meal;
import com.sobolev.spring.calorietrack.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MealService mealService;
    private final UserService userService;

    @Override
    public DailyReportDTO getDailyReport(Long userId) {
        User user = userService.getUserByIdInternal(userId);

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Meal> meals = mealService.findMealsByUserIdAndDate(userId, startOfDay, endOfDay);
        List<MealDTO> mealDTOs = meals.stream().map(this::convertToMealDTO).toList();

        return new DailyReportDTO(mealDTOs, user.getDailyCalories());
    }

    @Override
    public CalorieCheckDTO checkCalorieNorm(Long userId) {
        User user = userService.getUserByIdInternal(userId);

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Meal> meals = mealService.findMealsByUserIdAndDate(userId, startOfDay, endOfDay);

        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getMealDishes().stream())
                .mapToInt(mealDish -> mealDish.getDish().getCalories())
                .sum();

        boolean exceedsNorm = totalCalories > user.getDailyCalories();

        return new CalorieCheckDTO(totalCalories, user.getDailyCalories(), exceedsNorm);
    }

    @Override
    public MealHistoryDTO getMealHistory(Long userId) {
        List<Meal> meals = mealService.findAllMealsByUserId(userId);
        List<MealDTO> mealDTOs = meals.stream().map(this::convertToMealDTO).toList();

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
