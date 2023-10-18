package FeastList.menuItems;

import java.util.List;
import java.util.Optional;

import FeastList.users.vendors.Vendor;

public interface MenuItemService {

	MenuItem saveRestaurantMenuItem(Vendor restuarant, MenuItem menuItem);
	
	Optional<MenuItem> getMenuItemById(long itemId);

	List<MenuItem> getMenuItemsByRestaurant(int restuarantId);

	MenuItem updateRestaurantMenuItem(Vendor restuarant, MenuItem menuItem, long id);

	void deleteRestaurantMenuItem(Vendor vendor, long ItemId);

}
