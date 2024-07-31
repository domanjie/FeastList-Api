package FeastList.tray;

import FeastList.meal.domain.Meal;
import FeastList.meal.repository.JpaMealRepository;
import FeastList.tray.dto.TrayItemNotFoundException;
import FeastList.tray.dto.in.TrayItemQuantityDto;
import FeastList.tray.dto.out.TrayItemDtoOut;
import FeastList.tray.dto.in.TrayItemDtoIn;
import FeastList.tray.dto.out.VendorTrayItemsDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    public String addToTray(TrayItemDtoIn trayItemDtoIn) {
        var client =SecurityContextHolder .getContext().getAuthentication().getName();
        var trayItem= buildTrayItem(trayItemDtoIn,client);
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
        var clientId=SecurityContextHolder.getContext().getAuthentication().getName();
        var trayItems= trayRepo.findByTrayItemIdClientIdOrderByAddedAtAsc(clientId);
        var mealKeys=trayItems.stream().map(trayItem ->trayItem.getTrayItemId().getMealId()).toList();
        var mealKeysString=mealKeys.stream().map(Object::toString).collect(Collectors.joining(","));
        List<Meal> meals= mealRepository.findAllByIdOrderByIdList(mealKeys,mealKeysString);
        var list=new ArrayList<TrayItemDtoOut>();
        for (int i = 0; i <trayItems.size(); i++) {
            list.add(new TrayItemDtoOut(meals.get(i),trayItems.get(i).getAmount()));
        }
        return groupTraysItemsByVendor(list);
    }

    private List<VendorTrayItemsDto> groupTraysItemsByVendor(ArrayList<TrayItemDtoOut> list) {

        var map=new LinkedHashMap<String,ArrayList<TrayItemDtoOut>>();

        for(TrayItemDtoOut item:list){
            var key=item.meal().getVendorName();
            if(map.containsKey(key))
                map.get(key).add(item);
            else{
                map.put(key,new ArrayList<TrayItemDtoOut>(Collections.singleton(item)));
            }
        }
        var tray=new ArrayList<VendorTrayItemsDto>();
        map.forEach((key, value) -> tray.add(new VendorTrayItemsDto(key, value)));
        return tray;
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

    private TrayItem buildTrayItem(TrayItemDtoIn trayItemDtoIn, String client) {
        return TrayItem
                .builder()
                .trayItemId(new TrayItem.TrayItemId(
                        client,
                        UUID.fromString(trayItemDtoIn.mealId())))
                .amount(trayItemDtoIn.amount())
                .build();
    }
}
