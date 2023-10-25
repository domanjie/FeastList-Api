package FeastList.meals;

import java.util.List;

import FeastList.users.User;

public interface MealService {

	Meal saveUserMeal(Meal meal, User user);

	List<Meal> getRecentMeals(User user);

	String deleteUserTrayMealById(Long id, User user);

	List<Meal> getTrayMeals(User user);

}
