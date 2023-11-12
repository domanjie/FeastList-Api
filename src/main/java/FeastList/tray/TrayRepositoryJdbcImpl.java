package FeastList.tray;

import FeastList.meals.Meal;
import org.springframework.stereotype.Repository;

@Repository
public class TrayRepositoryJdbcImpl implements TrayRepository {

    @Override
    public void emptyTray() {

    }

    @Override
    public void addToTray(Meal meal) {

    }

    @Override
    public Tray getTray(String userId) {
        return null;
    }

    @Override
    public void deleteFromTray(Long mealId) {

    }
}
