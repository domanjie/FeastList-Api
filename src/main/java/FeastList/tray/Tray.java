package FeastList.tray;

import FeastList.meals.Meal;
import FeastList.users.User;
import lombok.Data;

import java.util.List;
@Data
public class Tray {

    private final List<TrayItemDto> trayMeals;

    private final String clientId;

}
