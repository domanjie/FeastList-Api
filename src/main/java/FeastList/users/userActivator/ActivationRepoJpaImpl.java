package FeastList.users.userActivator;

import org.springframework.data.repository.CrudRepository;

public interface ActivationRepoJpaImpl extends ActivationRepo , CrudRepository<Activation,String> {
}
