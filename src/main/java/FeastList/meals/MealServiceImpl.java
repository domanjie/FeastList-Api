package FeastList.meals;

import FeastList.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService{
    @Override
    public Meal saveUserMeal(Meal meal, User user) {
        return null;
    }

    @Override
    public List<Meal> getRecentMeals(User user) {
        return List.of();
    }

    @Override
    public String deleteUserTrayMealById(Long id, User user) {
        return null;
    }

    @Override
    public List<Meal> getTrayMeals(User user) {
        return List.of();
    }
}
