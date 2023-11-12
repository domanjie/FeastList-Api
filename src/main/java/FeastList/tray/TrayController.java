package FeastList.tray;

import FeastList.meals.Meal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/tray")
public class TrayController {
    private final TrayService trayService;
    public TrayController(TrayService trayService) {
        this.trayService=trayService;
    }

    @GetMapping
    public Tray getTray() {
        return trayService.getTray();
    }

    @PatchMapping(path="/clear")
    public String clearTray(){
      return  trayService.clearTray();
    }
    @PostMapping
    public void addToTray(@RequestBody Meal meal){
        trayService.addToTray(meal);
    }
    @DeleteMapping(path = "/delete/{id}")
    public void deleteFromTray(@PathVariable("id") Long id){
        trayService.deleteFromTray(id);
    }

}
