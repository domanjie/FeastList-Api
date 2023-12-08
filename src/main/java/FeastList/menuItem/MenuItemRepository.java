package FeastList.menuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {

    int saveMenuItem(MenuItem menuItem);

    Optional<MenuItem> getMenuItemById(int itemId);

    List<MenuItem> getByVendor(String restaurantId);

    void updateMenuItem(MenuItem menuItem, long id);

    void deleteMenuItem(long itemId);
}
