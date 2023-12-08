package FeastList.meals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class Meal  {

	private final int id;

	private final String name;

	private final double price;

	public final Timestamp  dateAdded;

	private final MealType mealType;

	private final String avatarUrl;

	private  String userId;

	private Set<MealItem> mealItems;

}
