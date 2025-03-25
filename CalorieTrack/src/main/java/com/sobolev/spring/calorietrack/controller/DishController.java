package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.model.Dish;
import com.sobolev.spring.calorietrack.service.DishService;
import com.sobolev.spring.calorietrack.util.DishValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;
    private final DishValidator dishValidator;

    @PostMapping
    public ResponseEntity<?> addDish(@Valid @RequestBody DishDTO dishDTO,
                                     BindingResult bindingResult) {
        return null; // доделать
    }
}
