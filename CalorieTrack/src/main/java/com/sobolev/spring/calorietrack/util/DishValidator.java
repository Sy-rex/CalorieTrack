package com.sobolev.spring.calorietrack.util;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DishValidator implements Validator {

    private final DishService dishService;

    @Override
    public boolean supports(Class<?> clazz) {
        return DishDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DishDTO dishDTO = (DishDTO) target;

        if(dishService.existsByName(dishDTO.getName())) {
            errors.rejectValue("name", "duplicate", "Dish with this name already exists");
        }
    }
}
