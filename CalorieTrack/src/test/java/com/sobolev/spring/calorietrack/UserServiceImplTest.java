package com.sobolev.spring.calorietrack;

import com.sobolev.spring.calorietrack.dto.UserDTO;
import com.sobolev.spring.calorietrack.dto.UserResponseDTO;
import com.sobolev.spring.calorietrack.exception.EntityNotFoundException;
import com.sobolev.spring.calorietrack.model.User;
import com.sobolev.spring.calorietrack.repository.UserRepository;
import com.sobolev.spring.calorietrack.service.UserServiceImpl;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setEmail("test@example.com");
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_EmailExists() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testDeleteUserById_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUserById(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserById_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUserById(1L));
    }
}
