package FeastList.tray;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/tray")
public class TrayController {
    private final TrayService trayService;

    public TrayController(TrayService trayService) {
        this.trayService=trayService;
    }
    @DeleteMapping
    public String clearTray(){
      return  trayService.clearTray();
    }
    @PostMapping
    public String addToTray(@RequestBody TrayItemDto trayItemDto) {
        return trayService.addToTray(trayItemDto);
    }
    @DeleteMapping(path = "/{id}")
    public void deleteFromTray(@PathVariable("id") String  mealId){
        trayService.deleteFromTray(UUID.fromString(mealId));
    }
    @GetMapping
    public List<TrayDto> getTray(){
        return trayService.getClientTray();
    }
}
