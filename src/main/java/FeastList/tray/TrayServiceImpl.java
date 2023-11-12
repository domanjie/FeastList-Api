package FeastList.tray;

import FeastList.meals.Meal;
import FeastList.meals.MealRepository;
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
        trayRepo.emptyTray();
        return "Tray successfully cleared";
    }

    @Override
    @Transactional
    public void addToTray(Meal meal) {
      trayRepo.addToTray(meal);
    }

    @Override
    public Tray getTray() {
        String userId =SecurityContextHolder.getContext().getAuthentication().getName();
        return trayRepo.getTray(userId);
    }
    @Override
    public void deleteFromTray(Long mealId) {

       trayRepo.deleteFromTray(mealId);
    }
}
