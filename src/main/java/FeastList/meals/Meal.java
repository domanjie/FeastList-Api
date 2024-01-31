package FeastList.meals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Meal  {

	private final int id;

	private final String name;

	public final Timestamp  dateAdded;

	private final MealType mealType;

	private final String avatarUrl;

	private  String userId;

	private final List<MealItem> mealItems;

	public double getPrice(){
		return mealItems.stream()
				.mapToDouble(MealItem::getPrice)
				.reduce(0,Double::sum);
	};
}
