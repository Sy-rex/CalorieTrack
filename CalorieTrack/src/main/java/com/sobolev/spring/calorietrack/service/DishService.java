package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.dto.DishResponseDTO;
import com.sobolev.spring.calorietrack.model.Dish;

import java.util.List;

public interface DishService {
    boolean existsByName(String name);
    boolean existsById(Long id);
    Dish getDishByIdInternal(Long id);
    DishResponseDTO addDish(DishDTO dishDTO);
    List<DishResponseDTO> getAllDishes();
    DishResponseDTO getDishById(Long id);
    void deleteDish(Long id);
}
