package FeastList.orders;

import java.util.List;
import java.util.Optional;

import FeastList.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public int saveUserOrder(@RequestBody Order order ){
		return orderService.saveOrder(order);
		
	}
	@GetMapping
	public ResponseEntity<List<Order>> getClientOrders(){
		List<Order> orders=orderService.getUserOrders();
		if(!orders.isEmpty())
			return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
	@GetMapping("/{id}")
	public ResponseEntity<Order> retrieveSingleOrder(@PathVariable("id") long orderId ){
		Optional<Order> order=orderService.getOrderById(orderId);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
