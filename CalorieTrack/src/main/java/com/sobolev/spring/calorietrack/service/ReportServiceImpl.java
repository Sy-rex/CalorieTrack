package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.CalorieCheckDTO;
import com.sobolev.spring.calorietrack.dto.DailyReportDTO;
import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealHistoryDTO;
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

        return new DailyReportDTO(meals, user.getDailyCalories());
    }

    @Override
    public CalorieCheckDTO checkCalorieNorm(Long userId) {
        User user = userService.getUserByIdInternal(userId);

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Meal> meals = mealService.findMealsByUserIdAndDate(userId, startOfDay, endOfDay);

        int totalCalories = meals.stream().mapToInt(meal -> meal.getMealDishes().stream()
                .mapToInt(mealDish -> mealDish.getDish().getCalories()).sum()).sum();

        boolean exceedsNorm = totalCalories > user.getDailyCalories();

        return new CalorieCheckDTO(totalCalories, user.getDailyCalories(), exceedsNorm);
    }

    @Override
    public MealHistoryDTO getMealHistory(Long userId) {
        List<Meal> meals = mealService.findAllMealsByUserId(userId);
        return new MealHistoryDTO(meals);
    }


}
