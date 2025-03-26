package com.sobolev.spring.calorietrack.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sobolev.spring.calorietrack.exception.InvalidEnumValueException;
import com.sobolev.spring.calorietrack.model.MealType;

import java.io.IOException;

public class MealDeserializer extends JsonDeserializer<MealType> {

    @Override
    public MealType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String mealValue = jsonParser.getText().toUpperCase();

        try{
            return MealType.valueOf(mealValue);
        }catch(IllegalArgumentException e){
            throw new InvalidEnumValueException("Invalid value for the 'mealType' field. Available values: BREAKFAST, LUNCH, DINNER, SNACK");
        }
    }
}
