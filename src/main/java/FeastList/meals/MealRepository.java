package FeastList.meals;

import java.util.List;
import java.util.Optional;

public interface MealRepository {
	int save(Meal meal);

	Optional<Meal> getMealById(Long mealId);

	Meal deleteMealById(Long mealId);

    List<Meal> getMealByRestaurants(String restaurantId);

	List<Meal> getMeals();
}
