package FeastList.menuItem;

import FeastList.infra.JpaRepositoryExtension;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MenuItemRepositoryJpaImpl extends MenuItemRepository,
        CrudRepository<MenuItem, UUID> ,
        JpaRepositoryExtension<MenuItem> {
}
