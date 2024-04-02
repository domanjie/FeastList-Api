package FeastList.menuItem;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MenuItemRepositoryJpaImpl extends MenuItemRepository, CrudRepository<MenuItem, UUID> {
}
