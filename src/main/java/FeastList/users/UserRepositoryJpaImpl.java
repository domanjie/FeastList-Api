package FeastList.users;

import FeastList.users.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJpaImpl extends CrudRepository<User,String> ,UserRepository {


}
