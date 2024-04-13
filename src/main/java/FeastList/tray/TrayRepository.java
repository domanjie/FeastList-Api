package FeastList.tray;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TrayRepository extends CrudRepository<TrayItem, TrayItem.TrayItemId> {
    @Query(value = "DELETE FROM tray WHERE client_id=:client", nativeQuery = true)
    void deleteByTrayItemIdClientId(@Param("client") String client);
}
