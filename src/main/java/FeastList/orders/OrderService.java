package FeastList.orders;

import java.util.List;
import java.util.Optional;

import FeastList.users.User;

public interface OrderService {

	int save(Order order);

	Optional<Order> getOrderById(Long orderId);

	List<Order> getUserOrders();
}
