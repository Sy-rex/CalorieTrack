package com.sobolev.spring.calorietrack.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MealDishId implements Serializable {
    private Long mealId;
    private Long dishId;
}
