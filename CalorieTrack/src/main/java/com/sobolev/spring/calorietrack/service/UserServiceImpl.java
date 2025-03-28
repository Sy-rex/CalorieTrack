package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.UserDTO;
import com.sobolev.spring.calorietrack.dto.UserResponseDTO;
import com.sobolev.spring.calorietrack.exception.EntityNotFoundException;
import com.sobolev.spring.calorietrack.model.User;
import com.sobolev.spring.calorietrack.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserDTO userDTO) {
        log.info("Создание нового пользователя с email {}", userDTO.getEmail());

        if (existsByEmail(userDTO.getEmail())) {
            log.error("Ошибка: email {} уже зарегистрирован", userDTO.getEmail());
            throw new EntityExistsException("User with email already exists");
        }

        return mapToResponseDTO(userRepository.save(mapToUser(userDTO)));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("Запрос списка всех пользователей");

        List<UserResponseDTO> users = userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();

        if (users.isEmpty()) {
            log.warn("Список пользователей пуст");
        } else {
            log.info("Загружено {} пользователей", users.size());
        }

        return users;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        log.info("Запрос пользователя с ID {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: пользователь с ID {} не найден", id);
                    return new EntityNotFoundException("User with ID: " + id + " not found");
                });

        log.info("Пользователь с ID {} найден", id);
        return mapToResponseDTO(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        log.info("Удаление пользователя с ID {}", id);

        if (!userRepository.existsById(id)) {
            log.error("Ошибка: пользователь с ID {} не найден", id);
            throw new EntityNotFoundException("User with ID: " + id + " not found");
        }

        userRepository.deleteById(id);
        log.info("Пользователь с ID {} успешно удалён", id);
    }

    @Override
    public User getUserByIdInternal(Long id) {
        log.info("Поиск пользователя по ID {} (внутренний метод)", id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: пользователь с ID {} не найден", id);
                    return new EntityNotFoundException("User with ID: " + id + " not found");
                });
    }

    private User mapToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
