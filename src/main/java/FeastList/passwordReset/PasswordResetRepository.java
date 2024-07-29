package FeastList.passwordReset;

import java.util.Optional;

public interface PasswordResetRepository {
    void deleteById(String resetCode);

    PasswordReset save(PasswordReset passwordReset);

    Optional<PasswordReset> findByPasswordResetCode(String resetCode);
}
