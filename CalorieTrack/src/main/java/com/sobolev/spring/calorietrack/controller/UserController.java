package com.sobolev.spring.calorietrack.controller;

import com.sobolev.spring.calorietrack.dto.UserDTO;
import com.sobolev.spring.calorietrack.dto.UserResponseDTO;
import com.sobolev.spring.calorietrack.service.UserService;
import com.sobolev.spring.calorietrack.util.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Управление пользователями")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Operation(summary = "Создание пользователя", description = "Добавляет нового пользователя в систему.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных пользователя")
    })
    @PostMapping
    public ResponseEntity<?> createUser(
            @Parameter(description = "Данные пользователя", required = true)
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) {
        log.info("POST /api/users - Создание нового пользователя: {}", userDTO.getEmail());
        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            log.warn("Ошибка валидации при создании пользователя: {}", errors);
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        UserResponseDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Получение списка пользователей", description = "Возвращает всех зарегистрированных пользователей.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("GET /api/users - Получение списка пользователей");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Получение пользователя по ID", description = "Возвращает информацию о пользователе по его идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @PathVariable Long id
    ) {
        log.info("GET /api/users/{} - Получение пользователя по ID", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Удаление пользователя", description = "Удаляет пользователя по его ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @PathVariable Long id
    ) {
        log.info("DELETE /api/users/{} - Удаление пользователя", id);
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
