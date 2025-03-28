package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealResponseDTO;
import com.sobolev.spring.calorietrack.service.MealService;
import com.sobolev.spring.calorietrack.util.MealValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final MealValidator mealValidator;

    @PostMapping
    public ResponseEntity<?> addMeal(@Valid @RequestBody MealDTO mealDTO,
                                     BindingResult bindingResult) {
        log.info("POST /api/meals - Добавление нового приёма пищи: {}", mealDTO.getMealDishes());
        mealValidator.validate(mealDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            log.warn("Ошибка валидации при добавлении приёма пищи: {}", errors);
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        return ResponseEntity.ok(mealService.addMeal(mealDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDTO> getMeal(@PathVariable Long id) {
        log.info("GET /api/meals/{} - Поиск приёма пищи по ID", id);
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        log.info("DELETE /api/meals/{} - Удаление приёма пищи", id);
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
