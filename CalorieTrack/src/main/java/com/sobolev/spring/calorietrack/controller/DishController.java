package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.dto.DishResponseDTO;
import com.sobolev.spring.calorietrack.service.DishService;
import com.sobolev.spring.calorietrack.util.DishValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;
    private final DishValidator dishValidator;

    @PostMapping
    public ResponseEntity<?> addDish(@Valid @RequestBody DishDTO dishDTO,
                                     BindingResult bindingResult) {
        log.info("POST /api/dishes - Добавление нового блюда: {}", dishDTO.getName());
        dishValidator.validate(dishDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            log.warn("Ошибка валидации при добавлении блюда: {}", errors);
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        DishResponseDTO createDish = dishService.addDish(dishDTO);
        return ResponseEntity.ok(createDish);
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> getAllDishes() {
        log.info("GET /api/dishes - Запрос всех блюд");
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDTO> getDishById(@PathVariable Long id) {
        log.info("GET /api/dishes/{} - Поиск блюда по ID", id);
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        log.info("DELETE /api/dishes/{} - Удаление блюда", id);
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }
}
