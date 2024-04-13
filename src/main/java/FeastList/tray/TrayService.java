package FeastList.tray;

import java.util.UUID;

public interface TrayService {
    String clearTray();

    String addToTray(TrayItemDto trayItemDto);

    String deleteFromTray(UUID mealId);
}
