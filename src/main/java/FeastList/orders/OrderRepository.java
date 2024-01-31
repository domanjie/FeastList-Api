package FeastList.orders;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> getOrdersByClientId(String clientId);

    Optional<Order> getOrderById(Long orderId);

    int saveOrder(Order order);

    List<Order> getOrdersByVendorId(String vendorId);

    Order updateOrder(Order order);
}
