package FeastList.passwordReset;

public interface PasswordResetRepository {
    void delete(String resetCode);

    PasswordReset save(PasswordReset passwordReset);

    PasswordReset findByResetCode(String resetCode);
}
