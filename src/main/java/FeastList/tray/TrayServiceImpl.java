package FeastList.tray;

import FeastList.meals.Meal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrayServiceImpl implements TrayService {
    private final TrayRepository trayRepo;

    public TrayServiceImpl(TrayRepository trayRepository){
        this.trayRepo=trayRepository;
    }
    @Override
    public String clearTray() {
        var userId=SecurityContextHolder.getContext().getAuthentication().getName();
        trayRepo.emptyUserTray(userId);
        return "Tray successfully cleared";
    }

    @Override
    @Transactional
    public void addToTray(TrayItemDto trayItemDto) {
        var userId=SecurityContextHolder.getContext().getAuthentication().getName();
      trayRepo.addToTray(trayItemDto,userId);
    }

    @Override
    public Tray getTray() {
        String userId =SecurityContextHolder.getContext().getAuthentication().getName();
        return trayRepo.getTray(userId);
    }
    @Override
    public void deleteFromTray(Long mealId) {
        String userId =SecurityContextHolder.getContext().getAuthentication().getName();
        trayRepo.deleteFromTray(mealId,userId);
    }
}
