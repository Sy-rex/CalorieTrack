package com.sobolev.spring.calorietrack.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Goal {
    WEIGHT_LOSS, MAINTENANCE, WEIGHT_GAIN;

    @JsonCreator
    public static Goal fromString(String value) {
        try {
            return Goal.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for the 'goal' field. Available values: WEIGHT_LOSS, MAINTENANCE, WEIGHT_GAIN");
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
