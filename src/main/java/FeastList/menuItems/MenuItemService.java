package FeastList.menuItems;

import java.util.List;
import java.util.Optional;

import FeastList.restaurants.Restaurant;

public interface MenuItemService {

	MenuItem saveRestaurantMenuItem(Restaurant restuarant, MenuItem menuItem);
	
	Optional<MenuItem> getMenuItemById(long itemId);

	List<MenuItem> getMenuItemsByRestaurant(int restuarantId);

	MenuItem updateRestaurantMenuItem(Restaurant restuarant, MenuItem menuItem, long id);

	void deleteRestaurantMenuItem(Restaurant restaurant, long ItemId);

}
