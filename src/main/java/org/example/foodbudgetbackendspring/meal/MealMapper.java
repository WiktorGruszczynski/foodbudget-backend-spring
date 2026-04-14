package org.example.foodbudgetbackendspring.meal;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.meal.dto.MealItemRequest;
import org.example.foodbudgetbackendspring.meal.dto.MealItemResponse;
import org.example.foodbudgetbackendspring.meal.dto.MealResponse;
import org.example.foodbudgetbackendspring.meal.model.Meal;
import org.example.foodbudgetbackendspring.meal.model.MealItem;
import org.example.foodbudgetbackendspring.product.ProductService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
class MealMapper {
    private final ProductService productService;

    public MealItemResponse mealItemToResponse(MealItem mealItem){
        return new MealItemResponse(
                mealItem.getId(),
                productService.mapToResponse(
                        mealItem.getProduct()
                ),
                mealItem.getQuantity(),
                mealItem.getUnit()
        );
    }

    private List<MealItemResponse> mealItemsToResponse(List<MealItem> mealItems){
        return mealItems.stream()
                .map(this::mealItemToResponse)
                .toList();
    }

    public MealItem toMealItem(MealItemRequest request){
        MealItem mealItem = new MealItem();

        mealItem.setProduct(
                productService.getProductReference(request.productId())
        );
        mealItem.setQuantity(request.quantity());
        mealItem.setUnit(request.unit());


        return mealItem;
    }


    public MealResponse toResponse(Meal meal) {
        return new MealResponse(
                meal.getId(),
                mealItemsToResponse(meal.getMealItems()),
                meal.getType(),
                meal.getDate()
        );
    }

    public List<MealResponse> toResponse(List<Meal> meals) {
        return meals.stream()
                .map(this::toResponse)
                .toList();
    }
}
