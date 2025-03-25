package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.UserDTO;
import com.sobolev.spring.calorietrack.dto.UserResponseDTO;
import com.sobolev.spring.calorietrack.exception.EntityNotFoundException;
import com.sobolev.spring.calorietrack.model.User;
import com.sobolev.spring.calorietrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = mapToUser(userDTO);
        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with ID: " + id + " not found"));
        return mapToResponseDTO(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with ID: " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    private User mapToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
