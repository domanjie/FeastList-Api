package FeastList.restaurants;

import java.util.List;
import java.util.Optional;


public interface RestaurantService {

	List<Restaurant> getRestaurants();

	Restaurant saveRestaurant(Restaurant restaurant);

	Optional<Restaurant> getRestaurantById(int restaurantId);

	void deleteRestaurant(int restaurantId);

}
