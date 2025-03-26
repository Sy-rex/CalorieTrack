package com.sobolev.spring.calorietrack.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 200,message = "Name should be between 2 and 200 characters long")
    private String name;

    @Email(message = "Incorrect email")
    @NotBlank
    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "age")
    @Min(value = 0,message = "Age must be greater than 0")
    @Max(value = 120,message = "Age must be less than 120")
    private Integer age;

    @Column(name = "weight")
    @DecimalMin("30.0")
    private Double weight;

    @Column(name = "height")
    @DecimalMin("100.0")
    private Double height;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal",nullable = false)
    @NotNull
    private Goal goal;

    @Column(name = "daily_calories", nullable = false)
    @Positive
    private Integer dailyCalories;

    @PrePersist
    @PreUpdate
    public void calculateDailyCalories() {
        if (weight == null || height == null || age == null || goal == null) {
            return;
        }
        this.dailyCalories = calculateCalories(weight, height, age, goal);
    }

    private int calculateCalories(Double weight, Double height, Integer age, Goal goal) {
        double bmr = 10 * weight + 6.25 * height - 5 * age + 5; // Харрис-Бенедикт для мужчин
        return switch (goal) {
            case WEIGHT_LOSS -> Math.max(1200, (int) (bmr * 1.2 - 500)); // Защита от слишком низких калорий
            case MAINTENANCE -> (int) (bmr * 1.55);
            case WEIGHT_GAIN -> (int) (bmr * 1.2 + 500);
        };
    }
}
