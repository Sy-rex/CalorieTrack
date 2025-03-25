package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.UserDTO;
import com.sobolev.spring.calorietrack.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    boolean existsByEmail(String email);
    UserResponseDTO createUser(UserDTO userDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    void deleteUserById(Long id);
}
