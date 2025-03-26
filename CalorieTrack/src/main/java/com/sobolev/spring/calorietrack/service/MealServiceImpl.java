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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        User user = userService.getUserByIdInternal(mealDTO.getUserId());

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealTime(mealDTO.getMealTime());
        meal.setMealType(mealDTO.getMealType());

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
        }

        Meal savedMeal = mealRepository.save(meal);

        savedMeal.getMealDishes().forEach(mealDish ->
                mealDish.getMealDishId().setMealId(savedMeal.getId())
        );

        mealRepository.save(savedMeal);

        return mapToMealResponseDTO(savedMeal);
    }

    @Override
    public MealResponseDTO getMealById(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal with ID: " + id + " not found"));

        return mapToMealResponseDTO(meal);
    }

    @Override
    @Transactional
    public void deleteMeal(Long id) {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException("Meal with ID: " + id + " not found");
        }

        mealRepository.deleteById(id);
    }

    @Override
    public List<Meal> findMealsByUserIdAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return mealRepository.findByUserIdAndMealTimeBetween(userId, startDate, endDate);
    }

    @Override
    public List<Meal> findAllMealsByUserId(Long userId) {
        return mealRepository.findByUserId(userId);
    }

    private Meal mapToMeal(MealDTO mealDTO) {
        return modelMapper.map(mealDTO, Meal.class);
    }

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
