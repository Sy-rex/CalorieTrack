package com.sobolev.spring.calorietrack.util;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MealValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MealDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MealDTO mealDTO = (MealDTO) target;

        // Проверка, существует ли пользователь
        if (!userService.existsById(mealDTO.getUserId())) {
            errors.rejectValue("userId", "user.notfound", "User with this ID does not exist");
        }

        if (mealDTO.getMealDishes() != null && mealDTO.getMealDishes().size() < 1) {
            errors.rejectValue("mealDishes", "mealDishes.tooFew", "Meal must contain at least one dish");
        }
    }
}
