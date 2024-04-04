package FeastList.infra;

public interface JpaRepositoryExtension<T> {
    public <S extends T> S persist(S entity);
}
