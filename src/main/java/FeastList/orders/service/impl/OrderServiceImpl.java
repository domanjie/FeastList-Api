package FeastList.orders.service.impl;

import FeastList.delivery.DeliveryService;
import FeastList.orders.Order;
import FeastList.orders.OrderRepository;
import FeastList.orders.OrderStatus;
import FeastList.orders.service.OrderService;
import FeastList.payment.PaymentService;
import FeastList.security.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl  implements OrderService {
    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;

    private final DeliveryService deliveryService;

    private final PaymentService paymentService;
    private OrderServiceImpl(OrderRepository orderRepository,
                             AuthenticationService authenticationService,
                             DeliveryService deliveryService,PaymentService paymentService){
        this.authenticationService=authenticationService;
        this.orderRepository=orderRepository;
        this.deliveryService=deliveryService;
        this.paymentService=paymentService;
    }

    @Override
    public int saveOrder(Order order) {

        return orderRepository.saveOrder(order);
    }

    @Override
    public Order makeOrder(Order order) {
        deliveryService.determineDeliveryCost(order);
        paymentService.makePayment(order.getTotalCost(),order.getPaymentMethod());
        order.setOrderStatus(OrderStatus.PENDING_DELIVERY);

        return orderRepository.updateOrder(order);
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
