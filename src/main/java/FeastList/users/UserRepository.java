package FeastList.users;

public interface UserRepository {

    User saveUser(User user);

    User findById(String s);

    User findByActivationCode(String activationCode);
}
