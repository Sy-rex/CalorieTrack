package com.sobolev.spring.calorietrack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "meal_time")
    private LocalDateTime mealTime;

    @OneToMany(mappedBy = "meal", orphanRemoval = true)
    private List<MealDish> mealDishes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false)
    @NotNull
    private MealType mealType;

    @PrePersist
    public void setMealTimeIfNull() {
        if (mealTime == null) {
            mealTime = LocalDateTime.now();
        }
    }
}
