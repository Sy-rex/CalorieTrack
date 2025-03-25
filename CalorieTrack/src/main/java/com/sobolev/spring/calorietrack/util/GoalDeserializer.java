package com.sobolev.spring.calorietrack.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sobolev.spring.calorietrack.exception.InvalidEnumValueException;
import com.sobolev.spring.calorietrack.model.Goal;

import java.io.IOException;

public class GoalDeserializer extends JsonDeserializer<Goal> {

    @Override
    public Goal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String goalValue = jsonParser.getText().toUpperCase();

        try {
            return Goal.valueOf(goalValue);
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("Неверное значение для поля 'goal'. Доступные значения: WEIGHT_LOSS, MAINTENANCE, WEIGHT_GAIN.");
        }
    }
}
