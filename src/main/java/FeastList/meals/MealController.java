package FeastList.meals;

import java.util.List;

import FeastList.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/meal",produces="application/json")
public class MealController {
	private final MealService mealService;
	
	
	public MealController(MealService mealService) {
		this.mealService=mealService;
	}
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Meal Meal(@RequestBody Meal meal ,@AuthenticationPrincipal User user){
		return mealService.saveUserMeal(meal,user);
	}
	@GetMapping("/tray")
	public ResponseEntity<List<Meal>> getUserTrayMeals(@AuthenticationPrincipal User user){
		List<Meal> trayMeals=mealService.getTrayMeals(user);
		if(!trayMeals.isEmpty()) {
			return new ResponseEntity<List<Meal>>(trayMeals, HttpStatus.OK);
		}else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("/tray/{id}")
	public ResponseEntity<String> deleteUserTrayMeal(@PathVariable("id") Long id ,@AuthenticationPrincipal User user ){
		return ResponseEntity.ok(mealService.deleteUserTrayMealById(id,user)) ;
	}
	@GetMapping("/history")
	public ResponseEntity<List<Meal>> getRecentUserMeals(@AuthenticationPrincipal User user){
		List<Meal> recentMeals=mealService.getRecentMeals(user);
		if(recentMeals.isEmpty()){
			return new ResponseEntity<List<Meal>>(recentMeals,HttpStatus.OK);
		}else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
}
