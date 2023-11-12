package FeastList.meals;

import FeastList.menuItems.MenuItem;
import FeastList.orders.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

@Data
@AllArgsConstructor
public class Meal  {

	private final int id;

	private final String name;

	private final double price;

	public final Timestamp  dateAdded;

	private final MealType mealType;

	private final String avatarUrl;

	private  String userId;

	private  Map<Integer,Integer> mealItems;

}
