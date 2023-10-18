package FeastList.users.vendors;

import java.util.List;
import java.util.Optional;


public interface VendorService {

	List<Vendor> getRestaurants();

	Vendor saveRestaurant(Vendor vendor);

	Optional<Vendor> getRestaurantById(int restaurantId);

	void deleteRestaurant(int restaurantId);

}
