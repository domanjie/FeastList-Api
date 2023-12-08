package FeastList.meals;

import FeastList.security.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService{

    private final MealRepository mealRepository;
    private final AuthenticationService authenticationService;
    public MealServiceImpl (MealRepository mealRepository, AuthenticationService authenticationService){
        this.mealRepository=mealRepository;
        this.authenticationService=authenticationService;
    }

    @Override
    public int saveMeal(Meal meal) {
        String presentUserId=authenticationService.getAuthenticatedUserId();
        meal.setUserId(presentUserId);
        return mealRepository.save(meal);
    }

    @Override
    public void deleteMeal(Long mealId) {
         mealRepository.deleteMealById(mealId );
    }

    @Override
    public Optional<Meal> getMealById(Long mealId) {
        return mealRepository.getMealById(mealId);
    }

    @Override
    public List<Meal> getMeals() {
        return mealRepository.getMeals();
    }


    public List<Meal> getMealsByRestaurant(String vendorId){
        return mealRepository.getMealByRestaurants (vendorId);
    }


}
