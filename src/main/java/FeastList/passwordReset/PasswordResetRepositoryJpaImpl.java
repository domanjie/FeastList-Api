package FeastList.passwordReset;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepositoryJpaImpl extends PasswordResetRepository, CrudRepository<PasswordReset,String> {
}
