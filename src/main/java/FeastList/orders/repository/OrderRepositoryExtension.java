package FeastList.orders.repository;


import FeastList.orders.dto.out.OrderDto;

import java.util.List;


public interface OrderRepositoryExtension {
    public List<OrderDto> getClientOrders(String client);
}
