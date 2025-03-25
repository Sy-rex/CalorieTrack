package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.dto.DishResponseDTO;
import com.sobolev.spring.calorietrack.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public DishResponseDTO addDish(DishDTO dishDTO) {
        return null;
    }

    @Override
    public List<DishResponseDTO> getAllDishes() {
        return List.of();
    }

    @Override
    public void deleteDish(Long id) {

    }
}
