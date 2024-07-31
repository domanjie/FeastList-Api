package FeastList.tray.dto.out;

import FeastList.meal.domain.Meal;

public record TrayItemDtoOut(Meal meal, int amount)  {
}
