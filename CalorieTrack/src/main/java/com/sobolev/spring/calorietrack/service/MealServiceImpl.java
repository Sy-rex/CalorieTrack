package com.sobolev.spring.calorietrack.service;

import com.sobolev.spring.calorietrack.dto.MealDTO;
import com.sobolev.spring.calorietrack.dto.MealDishDTO;
import com.sobolev.spring.calorietrack.dto.MealResponseDTO;
import com.sobolev.spring.calorietrack.exception.EntityNotFoundException;
import com.sobolev.spring.calorietrack.model.Dish;
import com.sobolev.spring.calorietrack.model.Meal;
import com.sobolev.spring.calorietrack.model.MealDish;
import com.sobolev.spring.calorietrack.model.User;
import com.sobolev.spring.calorietrack.model.embedded.MealDishId;
import com.sobolev.spring.calorietrack.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final DishService dishService;

    @Override
    @Transactional
    public MealResponseDTO addMeal(MealDTO mealDTO) {
        log.info("Добавление нового приёма пищи для пользователя с ID {}", mealDTO.getUserId());
        User user = userService.getUserByIdInternal(mealDTO.getUserId());

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealTime(mealDTO.getMealTime());
        meal.setMealType(mealDTO.getMealType());

        log.info("Обрабатываем блюда для приёма пищи...");

        // Обрабатываем каждое блюдо в приёме пищи
        for (MealDishDTO mealDishDTO : mealDTO.getMealDishes()) {
            Dish dish = dishService.getDishByIdInternal(mealDishDTO.getDishId());

            MealDish mealDish = new MealDish();
            mealDish.setMeal(meal);
            mealDish.setDish(dish);
            mealDish.setPortion(mealDishDTO.getPortion());

            // Инициализируем составной ключ
            MealDishId mealDishId = new MealDishId();
            mealDishId.setDishId(dish.getId());
            mealDish.setMealDishId(mealDishId);

            meal.getMealDishes().add(mealDish);
            log.info("Добавлено блюдо: {} ({} порций)", dish.getName(), mealDishDTO.getPortion());
        }

        Meal savedMeal = mealRepository.save(meal);

        savedMeal.getMealDishes().forEach(mealDish ->
                mealDish.getMealDishId().setMealId(savedMeal.getId())
        );

        mealRepository.save(savedMeal);
        log.info("Приём пищи успешно сохранён с ID {}", savedMeal.getId());

        return mapToMealResponseDTO(savedMeal);
    }

    @Override
    public MealResponseDTO getMealById(Long id) {
        log.info("Поиск приёма пищи по ID {}", id);
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка: Приём пищи с ID {} не найден", id);
                    return new EntityNotFoundException("Meal with ID: " + id + " not found");
                });

        return mapToMealResponseDTO(meal);
    }

    @Override
    @Transactional
    public void deleteMeal(Long id) {
        log.info("Удаление приёма пищи с ID {}", id);

        if (!mealRepository.existsById(id)) {
            log.error("Ошибка: Приём пищи с ID {} не найден, удаление невозможно", id);
            throw new EntityNotFoundException("Meal with ID: " + id + " not found");
        }

        mealRepository.deleteById(id);
        log.info("Приём пищи с ID {} успешно удалён", id);
    }

    @Override
    public List<Meal> findMealsByUserIdAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Поиск приёмов пищи для пользователя {} за период с {} по {}", userId, startDate, endDate);
        List<Meal> meals = mealRepository.findByUserIdAndMealTimeBetween(userId, startDate, endDate);

        if (meals.isEmpty()) {
            log.warn("Для пользователя {} не найдено приёмов пищи за указанный период", userId);
        }

        return meals;
    }

    @Override
    public List<Meal> findAllMealsByUserId(Long userId) {
        log.info("Получение всех приёмов пищи для пользователя {}", userId);
        List<Meal> meals = mealRepository.findByUserId(userId);

        if (meals.isEmpty()) {
            log.warn("Для пользователя {} не найдено приёмов пищи", userId);
        }

        return meals;
    }

//    private Meal mapToMeal(MealDTO mealDTO) {
//        return modelMapper.map(mealDTO, Meal.class);
//    }

    private MealResponseDTO mapToMealResponseDTO(Meal meal) {
        MealResponseDTO responseDTO = modelMapper.map(meal, MealResponseDTO.class);

        List<MealDishDTO> mealDishDTOs = meal.getMealDishes().stream()
                .map(mealDish -> {
                    MealDishDTO dto = new MealDishDTO();
                    dto.setDishId(mealDish.getDish().getId());
                    dto.setPortion(mealDish.getPortion());
                    return dto;
                })
                .toList();

        responseDTO.setMealDishes(mealDishDTOs);
        return responseDTO;
    }
}
