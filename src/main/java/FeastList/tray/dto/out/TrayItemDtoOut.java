package FeastList.tray.dto.out;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TrayItemDtoOut(UUID itemId , String mealName, String mealAvatar, BigDecimal price, int amount)  {
}
