package FeastList.orders.service.impl;

import FeastList.delivery.DeliveryService;
import FeastList.meal.domain.Meal;
import FeastList.meal.repository.JpaMealRepository;
import FeastList.orders.repository.OrderRepository;
import FeastList.orders.domain.Order;
import FeastList.orders.domain.OrderGroup;
import FeastList.orders.domain.OrderGroupPk;
import FeastList.orders.domain.OrderStatus;
import FeastList.orders.dto.in.OrderDto;
import FeastList.orders.service.OrderService;
import FeastList.payment.PaymentService;
import FeastList.users.UserRepository;
import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl  implements OrderService {

    private final OrderRepository orderRepository;

    private final DeliveryService deliveryService;

    private final UserRepository userRepository;
    private final JpaMealRepository mealRepository;

    private final PaymentService paymentService;
    private OrderServiceImpl(
            OrderRepository orderRepository,
                             DeliveryService deliveryService,JpaMealRepository mealRepository,UserRepository userRepository){
        this.orderRepository=orderRepository;
        this.deliveryService=deliveryService;
        this.paymentService=new PaymentService();
        this.mealRepository=mealRepository;
        this.userRepository=userRepository;
    }
    @Override
    public String processNewOrder(OrderDto orderDto) {

        var client= SecurityContextHolder.getContext().getAuthentication().getName();

        var id= UlidCreator.getUlid().toUuid();

        var  order=Order.builder().id(id)
                .clientId(client)
                .deliveryLocation(orderDto.deliveryLocation())
                .orderGroups(new ArrayList<OrderGroup>())
                .build();


        orderDto.orderGroupDtos().forEach(orderGroupDto -> {
            var mealIdList= orderGroupDto.orderItems().keySet().stream().toList();
            List<Meal> meals= mealRepository.findAllById(mealIdList);

            HashMap<Meal,Integer> orderItems=new HashMap<>();
            meals.forEach(meal ->orderItems.put(meal,orderGroupDto.orderItems().get(meal.getId())) );

            Object location= userRepository.getVendorLocation(orderGroupDto.vendorId());
            var deliveryFee=deliveryService.determineDeliveryCost(orderDto.deliveryLocation(),location);
            var  orderGroupPk= OrderGroupPk.builder()
                    .orderId(order.getId())
                    .vendor(orderGroupDto.vendorId())
                    .build();
            var orderGroup=OrderGroup.builder()
                    .orderItems(orderItems)
                    .orderStatus(OrderStatus.PENDING_DELIVERY)
                    .pk(orderGroupPk)
                    .deliveryFee(deliveryFee)
                    .build();
            order.addOrderGroup(orderGroup);
        });

        System.out.println(order.getTotalDeliveryCost());
        boolean paymentSuccessful=paymentService.makePaymentWithCreditCard(order.getTotalOrderCost(),orderDto.creditCardPaymentDetails());
        if(paymentSuccessful){
            orderRepository.persist(order);
        };
        return "order created Successfully";
    }
}
