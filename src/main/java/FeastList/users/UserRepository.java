package FeastList.users;

import java.util.List;

public interface UserRepository {

    String saveUser(User user);

    User findById(String s);

    User findByActivationCode(String activationCode);

    List<User> getUsersByRole(Role role);

    void updateUser(User user);
}
