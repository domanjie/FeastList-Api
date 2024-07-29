package FeastList.tray;

import java.util.List;
import java.util.UUID;

public interface TrayService {
    String clearTray();

    String addToTray(TrayItemDto trayItemDto);

    String deleteFromTray(UUID mealId);

    List<TrayDto> getClientTray();
}
