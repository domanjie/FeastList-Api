package FeastList.orders;

import java.util.List;
import java.util.Optional;

import FeastList.users.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface OrderService {

	Order save(Order order, User user);

	Optional<Order> getOrderById(UserDetails userDetails, Long orderId);

	List<Order> getOrders(UserDetails userDetails);

}
