package FeastList.menuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {

    MenuItem save(MenuItem menuItem);

    Optional<MenuItem> findById(UUID itemId);

}
