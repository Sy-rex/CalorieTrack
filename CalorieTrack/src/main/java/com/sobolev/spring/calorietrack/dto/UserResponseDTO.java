package com.sobolev.spring.calorietrack.dto;

import com.sobolev.spring.calorietrack.model.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для отображения информации о пользователе")
public class UserResponseDTO {

    @Schema(description = "ID пользователя", example = "1001")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;

    @Schema(description = "Электронная почта", example = "ivan.ivanov@example.com")
    private String email;

    @Schema(description = "Возраст", example = "30")
    private Integer age;

    @Schema(description = "Вес (в кг)", example = "75.5")
    private Double weight;

    @Schema(description = "Рост (в см)", example = "180.0")
    private Double height;

    @Schema(description = "Цель пользователя", example = "WEIGHT_LOSS")
    private Goal goal;

    @Schema(description = "Рассчитанная дневная норма калорий", example = "2500")
    private Integer dailyCalories;
}
