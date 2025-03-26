package com.sobolev.spring.calorietrack.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MealType {
    BREAKFAST, LUNCH, DINNER, SNACK;

    @JsonCreator
    public static MealType fromString(String value) {
        try {
            return MealType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for the 'mealType' field. Available values: BREAKFAST, LUNCH, DINNER, SNACK");
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
