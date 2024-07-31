package FeastList.tray.dto.out;

import java.util.List;

public record VendorTrayItemsDto(String vendorName, List<TrayItemDtoOut> TrayItems) {
}
