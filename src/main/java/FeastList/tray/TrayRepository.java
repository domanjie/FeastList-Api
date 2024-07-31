package FeastList.tray;

import FeastList.infra.JpaRepositoryExtension;
import FeastList.infra.JpaRepositoryExtensionImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface TrayRepository  extends CrudRepository<TrayItem, TrayItem.TrayItemId>, JpaRepositoryExtension<TrayItem> {
    @Query(value = "DELETE FROM tray WHERE client_id=:client", nativeQuery = true)
    void deleteByTrayItemIdClientId(@Param("client") String client);


    List<TrayItem> findByTrayItemIdClientIdOrderByAddedAtAsc(String clientId);
}
