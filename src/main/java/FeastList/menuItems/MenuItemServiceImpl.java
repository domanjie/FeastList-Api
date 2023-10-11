package FeastList.menuItems;

import FeastList.restaurants.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MenuItemServiceImpl implements MenuItemService{
    @Override
    public MenuItem saveRestaurantMenuItem(Restaurant restaurant, MenuItem menuItem) {
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
    public MenuItem updateRestaurantMenuItem(Restaurant restaurant, MenuItem menuItem, long id) {
        return null;
    }

    @Override
    public void deleteRestaurantMenuItem(Restaurant restaurant, long ItemId) {

    }
}
