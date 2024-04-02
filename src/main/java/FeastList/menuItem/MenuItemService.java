package FeastList.menuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemService {

	String saveVendorMenuItem( MenuItem menuItem);
	
	Optional<MenuItem> getMenuItemById(UUID itemId);

}
