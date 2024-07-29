package FeastList.orders.service.impl;

import FeastList.orders.domain.Order;
import FeastList.orders.service.OrderService;
import FeastList.orders.service.RunnerOrderService;

import java.util.List;

public class RunnerOrderServiceImpl implements RunnerOrderService {
    private final OrderService orderService;

    private RunnerOrderServiceImpl(
                                   OrderService orderService){
        this.orderService=orderService;
    }
    @Override
    public List<Order> poolRunnableOrders() {
        return null;
    }

    @Override
    public void acceptOrder(Order order) {
//      var runnerId=authenticationService.getAuthenticatedUser().getName();
//      order.setRunnerId(runnerId);
//      orderService.makeOrder(order);
    }

}
