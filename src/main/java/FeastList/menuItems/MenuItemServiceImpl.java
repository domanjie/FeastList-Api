package FeastList.menuItems;

import FeastList.users.vendors.Vendor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MenuItemServiceImpl implements MenuItemService{
    @Override
    public MenuItem saveRestaurantMenuItem(Vendor vendor, MenuItem menuItem) {
        return null;
    }

    @Override
    public Optional<MenuItem> getMenuItemById(long itemId) {
        return Optional.empty();
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) {
        return List.of();
    }

    @Override
    public MenuItem updateRestaurantMenuItem(Vendor vendor, MenuItem menuItem, long id) {
        return null;
    }

    @Override
    public void deleteRestaurantMenuItem(Vendor vendor, long ItemId) {

    }
}
