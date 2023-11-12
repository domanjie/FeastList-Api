package FeastList.meals;

import java.util.List;
import java.util.Optional;

public interface MealService {
	int saveMeal(Meal meal);

	Meal deleteMeal(Long mealId);

	Optional<Meal> getMeal(Long mealId);

	List<Meal> getMeals();

	List<Meal> getMealsByRestaurant(String restaurantId);
}
