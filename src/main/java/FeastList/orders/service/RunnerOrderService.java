package FeastList.orders.service;

import FeastList.orders.Order;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("")
public interface RunnerOrderService {
    List<Order> poolRunnableOrders();

    void acceptOrder(Order order);


}
