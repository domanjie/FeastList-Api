package FeastList.menuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {

	int saveRestaurantMenuItem( MenuItem menuItem);
	
	Optional<MenuItem> getMenuItemById(int itemId);

	List<MenuItem> getMenuItemsByVendor(String vendorId);

	void updateRestaurantMenuItem(MenuItem menuItem, long id);

	void deleteRestaurantMenuItem( long ItemId);

}
