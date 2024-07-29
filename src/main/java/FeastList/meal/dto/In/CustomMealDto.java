package FeastList.meal.dto.In;

import java.util.HashMap;

public record CustomMealDto(String vendorName,  HashMap<String,Integer> mealItems) {
}
