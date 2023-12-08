package FeastList.orders;

import FeastList.security.AuthenticationService;
import FeastList.users.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;

    private OrderServiceImpl(OrderRepository orderRepository,AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
        this.orderRepository=orderRepository;
    }
    @Override
    public int save(Order order) {
        //accept order with websockets
        //save the with status pending acceptance
        //direct the order and location details to all the various runners closest to the particular vendor listening
        //retrieve order and change the status to pending_delivery
        //send runner accepted messages to the client
        //with details of runner
        //order_delivered and order confirm by user
        return orderRepository.saveOrder(order);
    }

    @Override
    public Optional<Order> getOrderById(Long orderId){
        return orderRepository.getOrderById(orderId);
    }

    @Override
    public List<Order> getUserOrders() {
        String userId = authenticationService.getAuthenticatedUserId();
        return orderRepository.getOrdersByClientId(userId);
    }
}
