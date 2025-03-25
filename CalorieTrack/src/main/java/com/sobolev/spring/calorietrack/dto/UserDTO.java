package com.sobolev.spring.calorietrack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sobolev.spring.calorietrack.model.Goal;
import com.sobolev.spring.calorietrack.util.GoalDeserializer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    @Size(min = 2, max = 200, message = "Name should be between 2 and 200 characters long")
    private String name;

    @Email(message = "Incorrect email")
    @NotBlank
    private String email;

    @Min(value = 0,message = "Age must be greater than 0")
    @Max(value = 120,message = "Age must be less than 120")
    private Integer age;

    @DecimalMin("30.0")
    private Double weight;

    @DecimalMin("100.0")
    private Double height;

    @NotNull
    @JsonDeserialize(using = GoalDeserializer.class)
    private Goal goal;
}
