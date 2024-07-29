package FeastList.orders;

import java.util.List;

import FeastList.orders.domain.Order;
import FeastList.orders.dto.in.OrderDto;
import FeastList.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/orders",produces="application/json")
public class OrderController {

	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService=orderService;
	}
	@PostMapping
	public String saveUserOrder(@RequestBody OrderDto order){
		return orderService.processNewOrder(order);
	}
}
