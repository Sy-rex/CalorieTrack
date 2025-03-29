package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.dto.DishResponseDTO;
import com.sobolev.spring.calorietrack.service.DishService;
import com.sobolev.spring.calorietrack.util.DishValidator;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
@Tag(name = "Dish Controller", description = "Управление блюдами: добавление, просмотр, удаление")
public class DishController {

    private final DishService dishService;
    private final DishValidator dishValidator;

    @Operation(summary = "Создание нового блюда", description = "Добавляет новое блюдо в базу данных")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Блюдо успешно создано"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    })
    @PostMapping
    public ResponseEntity<?> addDish(
            @Valid @RequestBody DishDTO dishDTO,
            BindingResult bindingResult
    ) {
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

    @Operation(summary = "Получение списка всех блюд", description = "Возвращает список всех блюд из базы данных")
    @ApiResponse(responseCode = "200", description = "Список блюд успешно получен")
    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> getAllDishes() {
        log.info("GET /api/dishes - Запрос всех блюд");
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @Operation(summary = "Получение блюда по ID", description = "Возвращает блюдо по его уникальному идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Блюдо успешно найдено"),
            @ApiResponse(responseCode = "404", description = "Блюдо с указанным ID не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDTO> getDishById(
            @Parameter(description = "ID блюда для поиска", example = "1")
            @PathVariable Long id
    ) {
        log.info("GET /api/dishes/{} - Поиск блюда по ID", id);
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @Operation(summary = "Удаление блюда", description = "Удаляет блюдо из базы данных по его ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Блюдо успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Блюдо с указанным ID не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(
            @Parameter(description = "ID блюда для удаления", example = "1")
            @PathVariable Long id
    ) {
        log.info("DELETE /api/dishes/{} - Удаление блюда", id);
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }
}
