package FeastList.orders.service.impl;

import FeastList.delivery.DeliveryService;
import FeastList.meal.domain.Meal;
import FeastList.meal.repository.JpaMealRepository;
import FeastList.orders.OrderRepository;
import FeastList.orders.domain.Order;
import FeastList.orders.domain.OrderItemDetails;
import FeastList.orders.dto.in.OrderDto;
import FeastList.orders.service.OrderService;
import FeastList.payment.PaymentService;
import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OrderServiceImpl  implements OrderService {

    private final OrderRepository orderRepository;

    private final DeliveryService deliveryService;

    private final JpaMealRepository mealRepository;

    private final PaymentService paymentService;
    private OrderServiceImpl(
            OrderRepository orderRepository,
                             DeliveryService deliveryService,JpaMealRepository mealRepository){
        this.orderRepository=orderRepository;
        this.deliveryService=deliveryService;
        this.paymentService=new PaymentService();
        this.mealRepository=mealRepository;
    }

    @Override
    public String processNewOrder(OrderDto orderDto) {

        var client= SecurityContextHolder.getContext().getAuthentication().getName();
        var meals=mealRepository.findAllById(orderDto.orderItems().keySet());
        var mealAndDetails=new HashMap<Meal, OrderItemDetails>();
        for(Meal meal:meals){
                var detail=orderDto.orderItems().get(meal.getId());
                mealAndDetails.put(meal,detail);
        }
        var  order=Order.builder().id(UlidCreator.getUlid().toUuid())
                .clientId(client)
                .deliveryLocation(orderDto.Location())
                .mealAndDetails(mealAndDetails)
                .build();
        boolean paymentSuccessful=paymentService.makePaymentWithCreditCard(order.getTotalOrderCost(),orderDto.creditCardPaymentDetails());
        if(paymentSuccessful){
            orderRepository.save(order);
        };
        return "order created Successfully";
    }
}
