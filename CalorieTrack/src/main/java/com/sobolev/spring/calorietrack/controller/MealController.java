package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealResponseDTO;
import com.sobolev.spring.calorietrack.service.MealService;
import com.sobolev.spring.calorietrack.util.MealValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final MealValidator mealValidator;

    @PostMapping
    public ResponseEntity<?> addMeal(@Valid @RequestBody MealDTO mealDTO,
                                     BindingResult bindingResult) {
        mealValidator.validate(mealDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errors", bindingResult.getFieldErrors().stream()
                            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))));
        }

        return ResponseEntity.ok(mealService.addMeal(mealDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDTO> getMeal(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
