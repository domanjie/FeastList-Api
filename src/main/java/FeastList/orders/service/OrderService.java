package FeastList.orders.service;

import java.util.List;

import FeastList.orders.domain.Order;
import FeastList.orders.dto.in.OrderDto;

public interface OrderService {
	String processNewOrder(OrderDto order);
}
