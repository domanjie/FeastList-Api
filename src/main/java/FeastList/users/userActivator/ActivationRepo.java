package FeastList.users.userActivator;

import java.util.Optional;

public interface ActivationRepo {
    void SaveActivation(Activation activation);

    Optional<Activation> getByActivationCode(String activationCode);
}
