package FeastList.menuItem;

import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MenuItemServiceImpl implements MenuItemService{
    private final MenuItemRepository menuItemRepo;
    public MenuItemServiceImpl(MenuItemRepository menuItemRepo){
        this.menuItemRepo=menuItemRepo;
    }
    @Override
    public String saveVendorMenuItem( MenuItem menuItem) {
        System.err.println(menuItem);
        menuItem.setId(UlidCreator.getUlid().toUuid());
        System.err.println(menuItem);
        menuItemRepo.save(menuItem);
        return "Menu item saved successfully";
    }

    @Override
    public Optional<MenuItem> getMenuItemById(UUID itemId) {
        return menuItemRepo.findById(itemId);
    }

}
