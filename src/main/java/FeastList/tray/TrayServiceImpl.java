package FeastList.tray;

import FeastList.meal.domain.Meal;
import FeastList.meal.repository.JpaMealRepository;
import FeastList.security.AuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TrayServiceImpl implements TrayService {
    private final TrayRepository trayRepo;
    private final JpaMealRepository mealRepository;
    public TrayServiceImpl(TrayRepository trayRepository,
                           JpaMealRepository mealRepository){
        this.trayRepo=trayRepository;
        this.mealRepository=mealRepository;
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
    public String addToTray(TrayItemDto trayItemDto) {
        var client =SecurityContextHolder .getContext().getAuthentication().getName();
        var trayItem= buildTrayItem(trayItemDto,client);
        trayRepo.save(trayItem);
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
    public List<TrayDto> getClientTray() {
       var trayItems= trayRepo.findTrayItemsByClientId(SecurityContextHolder.getContext().getAuthentication().getName());
       var mealKey=trayItems.stream().map(trayItem -> trayItem.getTrayItemId().getMealId()).toList();
       List<Meal> meals= (List<Meal>) mealRepository.findAllById(mealKey);
       System.out.println(meals);
       System.out.println(trayItems);
       var tray=new ArrayList<TrayDto>();
        for (int i = 0; i <trayItems.size(); i++) {
            tray.add(new TrayDto(meals.get(i),trayItems.get(i).getAmount()));
        }
       return tray;
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
