package FeastList.tray;

import FeastList.meals.Meal;

public interface TrayService {
    String clearTray();

    void addToTray(Meal meal);

    Tray getTray();

    void deleteFromTray(Long id);
}
