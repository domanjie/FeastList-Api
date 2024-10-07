package FeastList.orders.repository;

import FeastList.infra.JpaRepositoryExtension;
import FeastList.orders.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> , JpaRepositoryExtension<Order>,OrderRepositoryExtension {
}
