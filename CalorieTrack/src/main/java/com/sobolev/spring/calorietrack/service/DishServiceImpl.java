package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.DishDTO;
import com.sobolev.spring.calorietrack.dto.DishResponseDTO;
import com.sobolev.spring.calorietrack.exception.EntityNotFoundException;
import com.sobolev.spring.calorietrack.model.Dish;
import com.sobolev.spring.calorietrack.repository.DishRepository;
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
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean existsByName(String name) {
        log.info("Проверка существования блюда с названием '{}': {}", name);
        return dishRepository.existsByName(name);
    }

    @Override
    public Dish getDishByIdInternal(Long id) {
        log.info("Поиск блюда по ID {}", id);
        return dishRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Блюдо с ID {} не найдено", id);
                    return new EntityNotFoundException("Dish with ID: " + id + " not found");
                });
    }

    @Override
    public boolean existsById(Long id) {
        log.info("Проверка существования блюда с ID {}: {}", id);
        return dishRepository.existsById(id);
    }

    @Override
    @Transactional
    public DishResponseDTO addDish(DishDTO dishDTO) {
        log.info("Добавление нового блюда: {}", dishDTO.getName());
        return mapToResponseDTO(dishRepository.save(mapToDish(dishDTO)));
    }

    @Override
    public List<DishResponseDTO> getAllDishes() {
        log.info("Получение списка всех блюд");
        List<DishResponseDTO> dishes = dishRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();

        if (dishes.isEmpty()) {
            log.warn("Список блюд пуст.");
        }

        return dishes;
    }

    @Override
    public DishResponseDTO getDishById(Long id) {
        log.info("Получение блюда по ID {}", id);
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Блюдо с ID {} не найдено", id);
                    return new EntityNotFoundException("Dish not found");
                });

        return mapToResponseDTO(dish);
    }

    @Override
    @Transactional
    public void deleteDish(Long id) {
        log.info("Удаление блюда с ID {}", id);

        if (!dishRepository.existsById(id)) {
            log.error("Ошибка: Блюдо с ID {} не найдено, удаление невозможно", id);
            throw new EntityNotFoundException("Dish with ID: " + id + " not found");
        }

        dishRepository.deleteById(id);
        log.info("Блюдо с ID {} успешно удалено", id);
    }

    private Dish mapToDish(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    private DishResponseDTO mapToResponseDTO(Dish dish) {
        return modelMapper.map(dish, DishResponseDTO.class);
    }
}
