package FeastList.orders.service.impl;

import FeastList.orders.Order;
import FeastList.orders.service.OrderService;
import FeastList.orders.service.RunnerOrderService;
import FeastList.security.AuthenticationService;

import java.util.List;

public class RunnerOrderServiceImpl implements RunnerOrderService {


    private final AuthenticationService authenticationService;

    private final OrderService orderService;
    private RunnerOrderServiceImpl(AuthenticationService authenticationService,
                                   OrderService orderService){
        this.authenticationService=authenticationService;
        this.orderService=orderService;
    }
    @Override
    public List<Order> poolRunnableOrders() {
        return null;
    }

    @Override
    public void acceptOrder(Order order) {
      var runnerId=authenticationService.getAuthenticatedUserId();
      order.setRunnerId(runnerId);
      orderService.makeOrder(order);

    }

}
