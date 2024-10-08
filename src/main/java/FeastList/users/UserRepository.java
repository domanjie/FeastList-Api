package FeastList.users;

import FeastList.infra.JpaRepositoryExtension;
import FeastList.users.domain.User;
import FeastList.users.dto.MiniVendorProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User,String>, JpaRepositoryExtension<User> {

    User save(User user);

    Optional<User> findById(String s);

    @Query(value = """
            SELECT vendor_username as vendor_name, header_photo
            FROM vendors
            """,nativeQuery = true)
    List<MiniVendorProjection> getVendors();

    //
    default String   getVendorLocation(String s){
        return "";
    };
}
