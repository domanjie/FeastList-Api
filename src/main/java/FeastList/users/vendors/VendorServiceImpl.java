package FeastList.users.vendors;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VendorServiceImpl implements VendorService {
    @Override
    public List<Vendor> getRestaurants() {
        return List.of();
    }

    @Override
    public Vendor saveRestaurant(Vendor vendor) {
        return null;
    }

    @Override
    public Optional<Vendor> getRestaurantById(int restaurantId) {
        return Optional.empty();
    }

    @Override
    public void deleteRestaurant(int restaurantId) {}
}
