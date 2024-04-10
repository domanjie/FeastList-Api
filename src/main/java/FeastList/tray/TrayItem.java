package FeastList.tray;

import FeastList.meal.domain.Meal;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="tray")
public class Tray {
    @Id
    private final String trayOwner;

    private final Map<Meal,Integer> trayItems ;

}
