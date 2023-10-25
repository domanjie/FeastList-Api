package FeastList.meals;

import java.util.Optional;

public interface MealRepository {

	Meal save(Meal meal);

	Optional<Iterable<Meal>> getRecentMeals();

	Optional<Iterable<Meal>> getTrayMeals();

	String deleteById(Long id);
}
