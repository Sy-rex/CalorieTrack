package com.sobolev.spring.calorietrack.util;

import com.sobolev.spring.calorietrack.dto.UserDTO;
import com.sobolev.spring.calorietrack.repository.UserRepository;
import com.sobolev.spring.calorietrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (userService.existsByEmail(userDTO.getEmail())) {
            errors.rejectValue("email", "error.user", "Email already in use");
        }
    }
}
