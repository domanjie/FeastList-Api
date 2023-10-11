package FeastList.orders;

import FeastList.users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order save(Order order, User user) {
        return null;
    }

    @Override
    public Optional<Order> getOrderById(UserDetails userDetails, Long orderId) {
        return Optional.empty();
    }

    @Override
    public List<Order> getOrders(UserDetails userDetails) {
        return List.of();
    }
}
