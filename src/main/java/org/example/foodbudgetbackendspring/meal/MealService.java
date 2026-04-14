package org.example.foodbudgetbackendspring.meal;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.meal.dto.MealItemRequest;
import org.example.foodbudgetbackendspring.meal.dto.MealItemResponse;
import org.example.foodbudgetbackendspring.meal.dto.MealResponse;
import org.example.foodbudgetbackendspring.meal.model.Meal;
import org.example.foodbudgetbackendspring.meal.model.MealItem;
import org.example.foodbudgetbackendspring.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final MealItemRepository mealItemRepository;
    private final MealMapper mealMapper;
    private final UserService userService;

    public List<MealResponse> getUserMealsFromRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        return mealMapper.toResponse(
                mealRepository.findAllByUserIdWithItemsAndProducts(userId, startDate, endDate)
        );
    }

    private Meal createMeal(UUID userId, LocalDate date, MealType mealType){
        Meal meal = new Meal();

        meal.setDate(date);
        meal.setType(mealType);
        meal.setUser(
                userService.getUserReference(userId)
        );

        return meal;
    }

    @Transactional
    public MealItemResponse addMealItem(UUID userId, MealItemRequest request) {
        Meal meal = mealRepository.findByUserIdAndDateAndType(
                userId,
                request.date(),
                request.mealType()
        ).orElseGet(
                () -> createMeal(userId, request.date(), request.mealType())
        );

        meal.addMealItem(
                mealMapper.toMealItem(request)
        );


        return mealMapper.mealItemToResponse(
                mealRepository.save(meal)
                        .getMealItems()
                        .getLast()
        );
    }

    public void removeMealItem(UUID mealItemId, UUID userId){
        Optional<MealItem> mealItemOptional = mealItemRepository.findByIdAndUserId(mealItemId, userId);
        mealItemOptional.ifPresent(mealItemRepository::delete);
    }
}
