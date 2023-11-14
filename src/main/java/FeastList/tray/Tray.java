package FeastList.tray;

import lombok.Data;

import java.util.List;


public record Tray(List<TrayItemDto> trayMeals, String clientId) {

}
