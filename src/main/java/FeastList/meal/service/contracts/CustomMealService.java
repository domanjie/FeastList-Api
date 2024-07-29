package FeastList.meal.service.contracts;

import FeastList.meal.dto.In.CustomMealDto;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('CLIENT')")
public interface CustomMealService {
    public String saveCustomMeal(CustomMealDto customMealDto);
}
