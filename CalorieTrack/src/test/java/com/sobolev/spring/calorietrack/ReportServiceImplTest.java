package com.sobolev.spring.calorietrack;

import com.sobolev.spring.calorietrack.dto.CalorieCheckDTO;
import com.sobolev.spring.calorietrack.dto.DailyReportDTO;
import com.sobolev.spring.calorietrack.dto.MealHistoryDTO;
import com.sobolev.spring.calorietrack.model.Meal;
import com.sobolev.spring.calorietrack.model.User;
import com.sobolev.spring.calorietrack.service.MealService;
import com.sobolev.spring.calorietrack.service.ReportServiceImpl;
import com.sobolev.spring.calorietrack.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private MealService mealService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReportServiceImpl reportService;

    private User user;
    private Meal meal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setDailyCalories(2000);

        meal = new Meal();
        meal.setId(1L);
        meal.setUser(user);
        meal.setMealTime(LocalDateTime.now());
    }

    @Test
    void testGetDailyReport() {
        when(userService.getUserByIdInternal(1L)).thenReturn(user);
        when(mealService.findMealsByUserIdAndDate(anyLong(), any(), any()))
                .thenReturn(List.of(meal));

        DailyReportDTO result = reportService.getDailyReport(1L);

        assertNotNull(result);
        assertEquals(1, result.getMeals().size());
        assertEquals(2000, result.getDailyCalorieNorm());
    }

    @Test
    void testGetMealHistory() {
        when(mealService.findAllMealsByUserId(1L)).thenReturn(List.of(meal));

        MealHistoryDTO result = reportService.getMealHistory(1L);

        assertNotNull(result);
        assertEquals(1, result.getMeals().size());
    }
}
