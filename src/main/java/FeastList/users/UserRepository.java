package FeastList.users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    String saveUser(User user);

    Optional<User> findById(String s);

    List<User> getUsersByRole(Role role);

    void updateUser(User user);
}
