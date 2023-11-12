package FeastList.tray;

import FeastList.meals.Meal;

public interface TrayRepository {
    void emptyTray();

    void addToTray(Meal meal);

    Tray getTray(String userId);

    void deleteFromTray(Long mealId);
}
