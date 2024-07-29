package FeastList.meal.service.contracts;

import FeastList.meal.dto.In.PreMadeMealDto;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('VENDOR')")
public interface PreMadeMealService {

    public String SavePreMadeMeal(PreMadeMealDto preMadeMealDto);
}
