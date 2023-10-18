package FeastList.users.vendors;

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
public class VendorController {
	private final VendorService vendorService;
	
	public VendorController(VendorService vendorService){
		this.vendorService = vendorService;
		
	}

@PostMapping(consumes="application/json")
public Vendor createVendor(@RequestBody Vendor vendor){
    return vendorService.saveRestaurant(vendor);
}
@DeleteMapping("/{id}")
public void deleteVendor(@PathVariable("id") int restaurantId){
    vendorService.deleteRestaurant(restaurantId);
}
@GetMapping
//search the difference between list and iterable
public ResponseEntity<List<Vendor>> getVendors(){
    List<Vendor> vendors = vendorService.getRestaurants();
    if (!vendors.isEmpty()) return new ResponseEntity<List<Vendor>>(vendors, HttpStatus.OK);
    else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
}
@GetMapping("/{id}")
public ResponseEntity<Vendor> getVendor(@PathVariable("id") int restaurantId ){
    Optional<Vendor> restaurant= vendorService.getRestaurantById(restaurantId);
    return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(restaurant.get(), HttpStatus.NOT_FOUND));
}
	

	
}
