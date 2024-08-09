package FeastList.tray.repository;

import FeastList.infra.JpaRepositoryExtension;
import FeastList.tray.TrayItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TrayRepository  extends CrudRepository<TrayItem, TrayItem.TrayItemId>, JpaRepositoryExtension<TrayItem>, TrayRepoExtension {


    @Query(value = "DELETE FROM tray WHERE client_id=:client", nativeQuery = true)
    void deleteByTrayItemIdClientId(@Param("client") String client);

}
