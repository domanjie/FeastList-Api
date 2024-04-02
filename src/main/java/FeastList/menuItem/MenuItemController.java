package FeastList.menuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/api/v1/menuItem",produces="application/json")
public class MenuItemController {
	private final MenuItemService menuService;
	public MenuItemController(MenuItemService menuService){
		this.menuService=menuService;
	}
	@PostMapping(consumes="application/json")
	public String saveMenuItem( @RequestBody MenuItem menuItem) {
		System.out.println(menuItem);
		return menuService.saveVendorMenuItem(menuItem);
	}
	@GetMapping("/{id}")
	public ResponseEntity<MenuItem> getItemById(@PathVariable("id") String id) {
		Optional<MenuItem> menuItem=menuService.getMenuItemById(UUID.fromString(id));
        return menuItem.map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
	}
}
