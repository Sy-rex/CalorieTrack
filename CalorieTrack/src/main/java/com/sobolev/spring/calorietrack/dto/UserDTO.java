package com.sobolev.spring.calorietrack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sobolev.spring.calorietrack.model.Goal;
import com.sobolev.spring.calorietrack.util.GoalDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для регистрации пользователя")
public class UserDTO {

    @NotBlank
    @Size(min = 2, max = 200, message = "Name should be between 2 and 200 characters long")
    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;

    @Email(message = "Incorrect email")
    @NotBlank
    @Schema(description = "Электронная почта", example = "ivan.ivanov@example.com")
    private String email;

    @Min(value = 0, message = "Age must be greater than 0")
    @Max(value = 120, message = "Age must be less than 120")
    @Schema(description = "Возраст", example = "30")
    private Integer age;

    @DecimalMin("30.0")
    @Schema(description = "Вес (в кг)", example = "75.5")
    private Double weight;

    @DecimalMin("100.0")
    @Schema(description = "Рост (в см)", example = "180.0")
    private Double height;

    @NotNull
    @JsonDeserialize(using = GoalDeserializer.class)
    @Schema(description = "Цель пользователя (например, сбросить вес, поддерживать вес)", example = "WEIGHT_LOSS")
    private Goal goal;
}
