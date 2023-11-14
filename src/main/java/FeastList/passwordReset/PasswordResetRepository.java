package FeastList.passwordReset;

public interface PasswordResetRepository {
    void delete(String resetCode);

    void save(PasswordReset passwordReset);

    PasswordReset findByResetCode(String resetCode);
}
