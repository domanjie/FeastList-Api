package FeastList.tray.repository;

import FeastList.tray.dto.out.VendorTrayItemsDto;

import java.util.List;

public interface TrayRepoExtension {
     List<VendorTrayItemsDto> getClientTray(String clientId);
}
