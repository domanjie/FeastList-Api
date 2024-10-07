package FeastList.orders;

import java.util.List;

import FeastList.orders.domain.Order;
import FeastList.orders.dto.in.OrderDto;
import FeastList.orders.repository.OrderRepository;
import FeastList.orders.service.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/orders",produces="application/json")
public class OrderController {

	private final OrderService orderService;
	
	private final OrderRepository orderRepository;
	public OrderController(OrderService orderService,OrderRepository orderRepository) {
		this.orderService=orderService;
		this.orderRepository=orderRepository;

	}
	@PostMapping
	public String saveUserOrder(@RequestBody OrderDto order){
		return orderService.processNewOrder(order);
	}
	@GetMapping
	public List<FeastList.orders.dto.out.OrderDto> getOrders(){
        return orderRepository.getClientOrders(SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
