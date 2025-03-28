package com.sobolev.spring.calorietrack;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealResponseDTO;
import com.sobolev.spring.calorietrack.model.Meal;
import com.sobolev.spring.calorietrack.model.User;
import com.sobolev.spring.calorietrack.repository.MealRepository;
import com.sobolev.spring.calorietrack.service.MealServiceImpl;
import com.sobolev.spring.calorietrack.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MealServiceImpl mealService;

    private Meal meal;
    private MealDTO mealDTO;
    private MealResponseDTO mealResponseDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        meal = new Meal();
        meal.setId(1L);
        meal.setUser(user);
        meal.setMealTime(LocalDateTime.now());

        mealDTO = new MealDTO();
        mealDTO.setUserId(1L);
        mealDTO.setMealTime(LocalDateTime.now());

        mealResponseDTO = new MealResponseDTO();
        mealResponseDTO.setId(1L);
    }

    @Test
    void testGetMealById_Success() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        when(modelMapper.map(meal, MealResponseDTO.class)).thenReturn(mealResponseDTO);

        MealResponseDTO result = mealService.getMealById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetMealById_NotFound() {
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> mealService.getMealById(1L));
    }
}
