package FeastList.delivery;

import FeastList.orders.Order;

public interface DeliveryService {
    void determineDeliveryCost(Order order);
}
