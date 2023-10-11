package FeastList.restaurants;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/v1/restaurants",produces="application/json")
public class RestaurantController {	
	private final RestaurantService restaurantService;
	
	public RestaurantController(RestaurantService restaurantService){
		this.restaurantService=restaurantService;
	}
	@GetMapping
//	search the difference between list and iterable
	public ResponseEntity<List<Restaurant>> getRestaurants(){
		List<Restaurant> restaurants=restaurantService.getRestaurants();
		if (!restaurants.isEmpty()) return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") int restaurantId ){
		Optional<Restaurant> restaurant= restaurantService.getRestaurantById(restaurantId);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(restaurant.get(), HttpStatus.NOT_FOUND));
    }
	
	@PostMapping(consumes="application/json")
	public Restaurant createRestaurant(@RequestBody Restaurant restaurant ){
		return restaurantService.saveRestaurant(restaurant);
	}
	@DeleteMapping("/{id}")
	public void deleteRestaurant(@PathVariable("id") int restaurantId){
		restaurantService.deleteRestaurant(restaurantId);
	}
	
}
