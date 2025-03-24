package com.sobolev.spring.calorietrack.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDishId implements Serializable {
    private Long mealId;
    private Long dishId;
}
