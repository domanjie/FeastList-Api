package FeastList.menuItems;

import java.util.List;
import java.util.Optional;

import FeastList.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/api/v1/menuItem",produces="application/json")
public class MenuItemController {
	private final MenuItemService menuService;
	public MenuItemController(MenuItemService menuService){
		this.menuService=menuService;
	}
	@PostMapping(consumes="application/json")
	public int RestaurantMenuItem( @RequestBody MenuItem menuItem) {
		return menuService.saveRestaurantMenuItem(menuItem);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MenuItem> getItemById(@PathVariable("id") int id) {
		Optional<MenuItem> menuItem=menuService.getMenuItemById(id);
        return menuItem.map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
	} 
	@GetMapping
	public ResponseEntity<List<MenuItem>> getItemsByVendor(@RequestParam(value="restaurant") String vendorId){
		List<MenuItem> items=menuService.getMenuItemsByVendor(vendorId);
        if(!items.isEmpty()) {
			return new ResponseEntity<List<MenuItem>>(items, HttpStatus.OK);
		}else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

	}
	@PatchMapping("/{id}")
	public void putMenuItem( @RequestBody MenuItem menuItem, @PathVariable("id") long id ) {
		 menuService.updateRestaurantMenuItem(menuItem,id);
	}
	@DeleteMapping("/{id}")
	public void DeleteMenuItem( @PathVariable("id") long itemId) {
		 menuService.deleteRestaurantMenuItem(itemId);
	}
}
