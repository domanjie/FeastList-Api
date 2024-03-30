package FeastList.users.userActivator;

import java.util.Optional;

public interface ActivationRepo {
    Activation save(Activation activation);

    Optional<Activation> getByActivationCode(String activationCode);

    void deleteById(String ActivationId);
}