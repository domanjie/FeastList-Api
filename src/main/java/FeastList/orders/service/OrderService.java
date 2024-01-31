package FeastList.orders.service;

import java.util.List;
import java.util.Optional;

import FeastList.orders.Order;

public interface OrderService {

	Order makeOrder(Order order);

	Optional<Order> getOrderById(Long orderId);

	List<Order> getUserOrders();
	int saveOrder(Order order);
}
