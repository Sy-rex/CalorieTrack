package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealResponseDTO;
import com.sobolev.spring.calorietrack.service.MealService;
import com.sobolev.spring.calorietrack.util.MealValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Meal Controller", description = "Управление приёмами пищи: добавление, просмотр, удаление")
public class MealController {

    private final MealService mealService;
    private final MealValidator mealValidator;

    @Operation(summary = "Добавление приёма пищи", description = "Позволяет создать новый приём пищи с указанием блюд")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Приём пищи успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    })
    @PostMapping
    public ResponseEntity<?> addMeal(
            @Valid @RequestBody MealDTO mealDTO,
            BindingResult bindingResult
    ) {
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

    @Operation(summary = "Получение приёма пищи по ID", description = "Возвращает информацию о приёме пищи по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Приём пищи найден"),
            @ApiResponse(responseCode = "404", description = "Приём пищи с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDTO> getMeal(
            @Parameter(description = "ID приёма пищи", example = "1")
            @PathVariable Long id
    ) {
        log.info("GET /api/meals/{} - Поиск приёма пищи по ID", id);
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @Operation(summary = "Удаление приёма пищи", description = "Удаляет приём пищи по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Приём пищи успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Приём пищи с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(
            @Parameter(description = "ID приёма пищи", example = "1")
            @PathVariable Long id
    ) {
        log.info("DELETE /api/meals/{} - Удаление приёма пищи", id);
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
