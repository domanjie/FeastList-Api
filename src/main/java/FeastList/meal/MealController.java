package FeastList.meal;

import FeastList.meal.dto.In.CustomMealDto;
import FeastList.meal.dto.In.PreMadeMealDto;
import FeastList.meal.dto.Out.PreMadeMealProjection;
import FeastList.meal.repository.JpaMealRepository;
import FeastList.meal.service.contracts.CustomMealService;
import FeastList.meal.service.contracts.PreMadeMealService;
import FeastList.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/meals", produces = "application/json")
public class MealController {
    private final PreMadeMealService preMadeMealService;

    private final CustomMealService customMealService;

    private final JpaMealRepository mealRepository;
    public MealController(PreMadeMealService preMadeMealService, CustomMealService customMealService, JpaMealRepository mealRepository){
        this.preMadeMealService=preMadeMealService;
        this.customMealService=customMealService;
        this.mealRepository=mealRepository;
    }
    @PostMapping(path = "/custom" ,consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveCustomMeal(@RequestBody CustomMealDto customMealDto){
       return customMealService.saveCustomMeal(customMealDto);
    }

    @PostMapping(path = "/preMade",consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String savePreMadeMeal(@RequestBody PreMadeMealDto preMadeMealDto){
        return preMadeMealService.SavePreMadeMeal(preMadeMealDto);
    }

    @GetMapping
    public List<PreMadeMealProjection> getClientMeals(){
      return mealRepository.findAllPreMadeMeal(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
