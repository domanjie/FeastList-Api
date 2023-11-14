package FeastList.tray;

import FeastList.meals.Meal;

public interface TrayService {
    String clearTray();

    void addToTray(TrayItemDto trayItemDto);

    Tray getTray();

    void deleteFromTray(Long id);
}
