package FeastList.tray;

import FeastList.tray.dto.in.TrayItemQuantityDto;
import FeastList.tray.dto.in.TrayItemDtoIn;
import FeastList.tray.dto.out.VendorTrayItemsDto;
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
    public String addToTray(@RequestBody TrayItemDtoIn trayItemDtoIn) {
        return trayService.addToTray(trayItemDtoIn);
    }

    @PatchMapping(path = "/{itemId}")
    public void alterTrayItemItemQuantity(@PathVariable("itemId") String itemId, @RequestBody TrayItemQuantityDto trayItemQuantityDto){
         trayService.changeTrayItemQuantity(UUID.fromString(itemId), trayItemQuantityDto);
    };
    @DeleteMapping(path = "/{id}")
    public void deleteFromTray(@PathVariable("id") String  mealId){
        trayService.deleteFromTray(UUID.fromString(mealId));
    }
    @GetMapping
    public List<VendorTrayItemsDto> getTray(){
        return trayService.getClientTray();
    }
}
