package com.sobolev.spring.calorietrack.model;

import com.sobolev.spring.calorietrack.model.embedded.MealDishId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"meal", "dish"})
@ToString(exclude = {"meal", "dish"})
@Table(name = "meal_dishes")
public class MealDish {

    @EmbeddedId
    private MealDishId mealDishId;

    @ManyToOne
    @MapsId("mealId")
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @Positive
    @Column(name = "portion", nullable = false)
    private int portion;
}
