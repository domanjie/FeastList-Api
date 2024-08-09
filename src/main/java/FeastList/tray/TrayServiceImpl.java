package FeastList.tray;
import FeastList.tray.dto.TrayItemNotFoundException;
import FeastList.tray.dto.in.TrayItemQuantityDto;
import FeastList.tray.dto.in.TrayItemDtoIn;
import FeastList.tray.dto.out.VendorTrayItemsDto;
import FeastList.tray.repository.TrayRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TrayServiceImpl implements TrayService {
    private final TrayRepository trayRepo;
    public TrayServiceImpl(TrayRepository trayRepository){
        this.trayRepo=trayRepository;
    }
    @Override
    @Transactional
    public String clearTray() {
        var client = SecurityContextHolder.getContext().getAuthentication().getName();
        trayRepo.deleteByTrayItemIdClientId(client);
        return "Tray cleared successfully";
    }

    @Override
    @Transactional
    public String addToTray(TrayItemDtoIn trayItemDtoIn) {
        var clientId =SecurityContextHolder .getContext().getAuthentication().getName();
        var mealId =UUID.fromString(trayItemDtoIn.mealId());
        var trayItem= TrayItem
                .builder()
                .trayItemId(new TrayItem.TrayItemId(clientId,mealId))
                .amount(trayItemDtoIn.amount())
                .build();

        trayRepo.persist(trayItem);
        return "meal added successfully";

    }

    @Override
    @Transactional
    public String deleteFromTray(UUID mealId) {
        var client =SecurityContextHolder.getContext().getAuthentication().getName();
        var trayItemId= new TrayItem.TrayItemId(client,mealId);
        trayRepo.deleteById(trayItemId);
        return "meal removed successfully";
    }

    @Override
    public List<VendorTrayItemsDto> getClientTray() {
        var clientId =SecurityContextHolder.getContext().getAuthentication().getName();
        return trayRepo.getClientTray(clientId);
    }


    @Transactional
    @Override
    public void changeTrayItemQuantity(UUID mealId, TrayItemQuantityDto trayItemQuantityDto) {
        var client=SecurityContextHolder.getContext().getAuthentication().getName();
        var id =new TrayItem.TrayItemId(client,mealId);
        var trayITem=trayRepo.findById(id).orElseThrow(()->new TrayItemNotFoundException(""));
        if(trayItemQuantityDto.quantity()==0){
            trayRepo.deleteById(id);
            return ;
        }
        trayITem.changeAmount(trayItemQuantityDto.quantity());
    }
}
