package FeastList.restaurants;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Override
    public List<Restaurant> getRestaurants() {
        return List.of();
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public Optional<Restaurant> getRestaurantById(int restaurantId) {
        return Optional.empty();
    }

    @Override
    public void deleteRestaurant(int restaurantId) {}
}
