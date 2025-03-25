package com.sobolev.spring.calorietrack.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealDishId implements Serializable {
    private Long mealId;
    private Long dishId;
}
