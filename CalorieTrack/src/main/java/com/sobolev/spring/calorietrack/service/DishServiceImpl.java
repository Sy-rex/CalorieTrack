package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.dto.DishResponseDTO;
import com.sobolev.spring.calorietrack.exception.EntityNotFoundException;
import com.sobolev.spring.calorietrack.model.Dish;
import com.sobolev.spring.calorietrack.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean existsByName(String name) {
        return dishRepository.existsByName(name);
    }

    @Override
    public Dish getDishByIdInternal(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Dish with ID: " + id + " not found"));
    }

    @Override
    public boolean existsById(Long id) {
        return dishRepository.existsById(id);
    }

    @Override
    @Transactional
    public DishResponseDTO addDish(DishDTO dishDTO) {
        return mapToResponseDTO(dishRepository.save(mapToDish(dishDTO)));
    }

    @Override
    public List<DishResponseDTO> getAllDishes() {
        return dishRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public DishResponseDTO getDishById(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found"));
        return mapToResponseDTO(dish);
    }

    @Override
    @Transactional
    public void deleteDish(Long id) {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException("Dish with ID: " + id + " not found");
        }

        dishRepository.deleteById(id);
    }

    private Dish mapToDish(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    private DishResponseDTO mapToResponseDTO(Dish dish) {
        return modelMapper.map(dish, DishResponseDTO.class);
    }
}
