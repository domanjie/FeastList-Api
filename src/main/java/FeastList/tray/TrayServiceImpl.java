package FeastList.tray;

import FeastList.security.AuthenticationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TrayServiceImpl implements TrayService {
    private final TrayRepository trayRepo;

    private final AuthenticationService authenticationService;
    public TrayServiceImpl(TrayRepository trayRepository, AuthenticationService authenticationService){
        this.trayRepo=trayRepository;
        this.authenticationService = authenticationService;
    }
    @Override
    @Transactional
    public String clearTray() {
        var client =authenticationService.getAuthenticatedUser().getName();
        trayRepo.deleteByTrayItemIdClientId(client);
        return "Tray cleared successfully";
    }

    @Override
    @Transactional
    public String addToTray(TrayItemDto trayItemDto) {
        var client =authenticationService.getAuthenticatedUser().getName();
        var trayItem= buildTrayItem(trayItemDto,client);
        trayRepo.save(trayItem);
        return "meal added successfully";

    }



    @Override
    @Transactional
    public String deleteFromTray(UUID mealId) {
        var client =authenticationService.getAuthenticatedUser().getName();
        var trayItemId= new TrayItem.TrayItemId(client,mealId);
        trayRepo.deleteById(trayItemId);
        return "meal removed successfully";
    }
    private TrayItem buildTrayItem(TrayItemDto trayItemDto, String client) {
        return TrayItem
                .builder()
                .trayItemId(new TrayItem.TrayItemId(
                        client,
                        UUID.fromString(trayItemDto.mealId())))
                .amount(trayItemDto.amount())
                .build();
    }
}
