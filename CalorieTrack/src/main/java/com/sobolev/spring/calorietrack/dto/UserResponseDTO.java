package com.sobolev.spring.calorietrack.dto;

import com.sobolev.spring.calorietrack.model.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Double weight;
    private Double height;
    private Goal goal;
    private Integer dailyCalories;
}
