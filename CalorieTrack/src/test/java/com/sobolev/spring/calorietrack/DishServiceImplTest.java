package com.sobolev.spring.calorietrack;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.dto.DishResponseDTO;
import com.sobolev.spring.calorietrack.exception.EntityNotFoundException;
import com.sobolev.spring.calorietrack.model.Dish;
import com.sobolev.spring.calorietrack.repository.DishRepository;
import com.sobolev.spring.calorietrack.service.DishServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DishServiceImplTest {
    @Mock
    private DishRepository dishRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DishServiceImpl dishService;

    private Dish dish;
    private DishDTO dishDTO;
    private DishResponseDTO dishResponseDTO;

    @BeforeEach
    void setUp() {
        dish = new Dish();
        dish.setId(1L);
        dish.setName("Salad");
        dish.setCalories(150);

        dishDTO = new DishDTO();
        dishDTO.setName("Salad");
        dishDTO.setCalories(150);

        dishResponseDTO = new DishResponseDTO();
        dishResponseDTO.setId(1L);
        dishResponseDTO.setName("Salad");
        dishResponseDTO.setCalories(150);
    }

    @Test
    void testGetDishById_Success() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(modelMapper.map(dish, DishResponseDTO.class)).thenReturn(dishResponseDTO);

        DishResponseDTO result = dishService.getDishById(1L);

        assertNotNull(result);
        assertEquals("Salad", result.getName());
        assertEquals(150, result.getCalories());
    }

    @Test
    void testGetDishById_NotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> dishService.getDishById(1L));
    }

    @Test
    void testAddDish_Success() {
        when(modelMapper.map(dishDTO, Dish.class)).thenReturn(dish);
        when(dishRepository.save(dish)).thenReturn(dish);
        when(modelMapper.map(dish, DishResponseDTO.class)).thenReturn(dishResponseDTO);

        DishResponseDTO result = dishService.addDish(dishDTO);

        assertNotNull(result);
        assertEquals("Salad", result.getName());
        verify(dishRepository, times(1)).save(any(Dish.class));
    }
}
