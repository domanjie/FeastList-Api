package FeastList.tray;

import FeastList.meals.Meal;

public interface TrayRepository {
    void emptyUserTray(String userId);

    void addToTray(TrayItemDto trayItemDto,String clientId);

    Tray getTray(String userId);

    void deleteFromTray(Long mealId,String userId);
}
