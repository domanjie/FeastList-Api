package FeastList.menuItems;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MenuItemServiceImpl implements MenuItemService{
    private final MenuItemRepository menuItemRepo;
    public MenuItemServiceImpl(MenuItemRepository menuItemRepo){
        this.menuItemRepo=menuItemRepo;
    }
    @Override
    public int saveRestaurantMenuItem( MenuItem menuItem) {
        return menuItemRepo.saveMenuItem(menuItem);
    }

    @Override
    public Optional<MenuItem> getMenuItemById(int itemId) {
        return menuItemRepo.getMenuItemById(itemId);
    }

    @Override
    public List<MenuItem> getMenuItemsByVendor(String vendorId) {
        return menuItemRepo.getByVendor(vendorId);
    }

    @Override
    public void updateRestaurantMenuItem( MenuItem menuItem, long id) {
         menuItemRepo.updateMenuItem(menuItem, id);
    }

    @Override
    public void deleteRestaurantMenuItem(long itemId) {
         menuItemRepo.deleteMenuItem(itemId);
    }
}
