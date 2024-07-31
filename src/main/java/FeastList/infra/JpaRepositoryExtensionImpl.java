package FeastList.infra;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaRepositoryExtensionImpl<T> implements JpaRepositoryExtension<T> {

    private final EntityManager entityManager;
    public JpaRepositoryExtensionImpl(EntityManager entityManager ){
        this.entityManager=entityManager;
    }
    @Override
    @Transactional
    public <S extends T> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }
}
