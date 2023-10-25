package FeastList.orders;

import java.util.List;
import java.util.Optional;

import FeastList.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
	public Order saveUserOrder(@RequestBody Order order ,@AuthenticationPrincipal User user){
		return orderService.save(order,user);
		
	}
	@GetMapping
	public ResponseEntity<List<Order>> retrieveCurrentOrders(@AuthenticationPrincipal UserDetails userDetails){
		List<Order> orders=orderService.getOrders(userDetails);
		if(!orders.isEmpty()){
			return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
		}else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
	@GetMapping("/{id}")
	public ResponseEntity<Order> retrieveSingleOrder(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") long orderId ){
		Optional<Order> order=orderService.getOrderById(userDetails,orderId);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
	
}
