package FeastList.tray;

import FeastList.tray.dto.in.TrayItemQuantityDto;
import FeastList.tray.dto.in.TrayItemDtoIn;
import FeastList.tray.dto.out.VendorTrayItemsDto;

import java.util.List;
import java.util.UUID;

public interface TrayService {
    String clearTray();

    String addToTray(TrayItemDtoIn trayItemDtoIn);

    String deleteFromTray(UUID mealId);

    List<VendorTrayItemsDto> getClientTray();

    void changeTrayItemQuantity(UUID uuid, TrayItemQuantityDto trayItemQuantityDto);
}
