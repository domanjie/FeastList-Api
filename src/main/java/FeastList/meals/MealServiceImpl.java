package FeastList.meals;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService{

    private final MealRepository mealRepository;
    public MealServiceImpl (MealRepository mealRepository){
        this.mealRepository=mealRepository;
    }

    @Override
    public int saveMeal(Meal meal) {
        meal.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        return mealRepository.save(meal);
    }

    @Override
    public Meal deleteMeal(Long mealId) {
        return mealRepository.deleteMealById(mealId );
    }

    @Override
    public Optional<Meal> getMeal(Long mealId) {
        return mealRepository.getMealById(mealId);
    }

    @Override
    public List<Meal> getMeals() {
        return mealRepository.getMeals();
    }


    public List<Meal> getMealsByRestaurant(String restaurantId){
        return mealRepository.getMealByRestaurants (restaurantId);
    }


}
