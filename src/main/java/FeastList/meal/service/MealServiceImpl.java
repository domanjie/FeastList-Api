package FeastList.meal.service;

import FeastList.meal.domain.CustomMeal;
import FeastList.meal.domain.PreMadeMeal;
import FeastList.meal.dto.In.CustomMealDto;
import FeastList.meal.dto.In.PreMadeMealDto;
import FeastList.meal.repository.JpaMealRepository;
import FeastList.meal.service.contracts.CustomMealService;
import FeastList.meal.service.contracts.PreMadeMealService;
import FeastList.menuItem.MenuItem;
import FeastList.menuItem.MenuItemRepositoryJpaImpl;
import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class MealServiceImpl implements CustomMealService, PreMadeMealService {

    private final MenuItemRepositoryJpaImpl menuItemRepositoryJpa;

    private final JpaMealRepository mealRepositoryJpa;
    public  MealServiceImpl( JpaMealRepository mealRepositoryJpa,
                            MenuItemRepositoryJpaImpl menuItemRepositoryJpa){
        this.mealRepositoryJpa=mealRepositoryJpa;
        this.menuItemRepositoryJpa=menuItemRepositoryJpa;
    }
    @Override
    @Transactional
    public String saveCustomMeal(CustomMealDto customMealDto) {
        var meal = CustomMeal
                .builder()
                .id(UlidCreator.getUlid().toUuid())
                .clientId(SecurityContextHolder.getContext().getAuthentication().getName())
                .vendorId(customMealDto.vendorName())
                .mealItems(fetchMealItems(customMealDto.mealItems()))
                .build();
        mealRepositoryJpa.save(meal);

        return "meal saved successfully";
    }


    @Override
    @Transactional
    public String SavePreMadeMeal(PreMadeMealDto preMadeMealDto) {
        var meal= PreMadeMeal.builder()
                .id(UlidCreator.getUlid().toUuid())
                .mealName(preMadeMealDto.mealName())
                .price(preMadeMealDto.price())
                .vendorId(SecurityContextHolder.getContext().getAuthentication().getName())
                .avatarUrl(preMadeMealDto.avatarUrl())
                .build();
        mealRepositoryJpa.persist(meal);
        return "meal saved successfully";
    }

    private HashMap<MenuItem, Integer> fetchMealItems(HashMap<String, Integer> dtoMealItems) {
        var mealItemKeys=dtoMealItems.keySet()
                .stream().map(UUID::fromString).toList();

        var mealItems=new HashMap<MenuItem ,Integer>();

        for (MenuItem menuItem : menuItemRepositoryJpa.findAllById(mealItemKeys)) {
            mealItems.put(menuItem,dtoMealItems.get(menuItem.toString()));
        }
        return mealItems;
    }

}
