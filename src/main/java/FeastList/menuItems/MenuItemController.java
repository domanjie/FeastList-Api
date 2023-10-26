package FeastList.menuItems;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="/api/v1/menuItem",produces="application/json")
public class MenuItemController {
	private final MenuItemService menuService;
	public MenuItemController(MenuItemService menuService){
		this.menuService=menuService;
	}
	@PostMapping(consumes="application/json")
	public MenuItem RestaurantMenuItem(@AuthenticationPrincipal Vendor vendor, @RequestBody MenuItem menuItem)
	{
		return menuService.saveRestaurantMenuItem(vendor,menuItem);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<MenuItem> getItemById(@PathVariable("id") long id)
	{
		Optional<MenuItem> menuItem=menuService.getMenuItemById(id);
        return menuItem.map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
	} 
	@GetMapping
	public String getItemByRestaurant(){
//		@RequestParam(value="restaurant") int restuarantId
//		ResponseEntity<List<MenuItem>>
		//		List<MenuItem> items=menuService.getMenuItemsByRestaurant(restuarantId);
//        if(!items.isEmpty()) {.
//			return new ResponseEntity<List<MenuItem>>(items, HttpStatus.OK);
//		}else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		return "i am happy 🤣🤣";
	}
	@PutMapping("/{id}")
	public MenuItem putMenuItem(@AuthenticationPrincipal Vendor vendor, @RequestBody MenuItem menuItem, @PathVariable("id") long id ) {
		return menuService.updateRestaurantMenuItem(vendor,menuItem,id);
		
	}
	@DeleteMapping("/{id}")
	public void DeleteMenuItem(@AuthenticationPrincipal Vendor vendor, @PathVariable("id") long itemId)
	{
			menuService.deleteRestaurantMenuItem(vendor,itemId);
	}
}
