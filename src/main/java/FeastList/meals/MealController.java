package FeastList.meals;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/meal",produces="application/json")
public class MealController {
	private final MealService mealService;

	public MealController(MealService mealService) {
		this.mealService=mealService;
	}
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public int Meal(@RequestBody Meal meal){
		return mealService.saveMeal(meal);
	}
	@GetMapping("/{id}")
	public Optional<Meal> meal (@PathVariable Long mealId){
		return mealService.getMealById(mealId);
	}
	@DeleteMapping("/{id}")
	public void deleteMeal(@PathVariable Long mealId){
		 mealService.deleteMeal(mealId);
	}
	@GetMapping
	public List<Meal> getMeals(){
		return mealService.getMeals();
	}
	@GetMapping("/res")
	public List<Meal> getMealsByRestaurant(@RequestParam(required = true) String restaurantId){
		return mealService.getMealsByRestaurant(restaurantId);
	}

}
