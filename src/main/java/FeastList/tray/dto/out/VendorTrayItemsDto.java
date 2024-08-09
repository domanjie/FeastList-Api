package FeastList.tray.dto.out;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record VendorTrayItemsDto(String vendorName, String vendorAvatar , ArrayList<TrayItemDtoOut> trayItems) {
}
