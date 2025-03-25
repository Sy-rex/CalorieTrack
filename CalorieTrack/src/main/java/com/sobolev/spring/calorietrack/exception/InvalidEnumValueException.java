package com.sobolev.spring.calorietrack.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class InvalidEnumValueException extends JsonProcessingException {
    public InvalidEnumValueException(String message) {
        super(message);
    }
}