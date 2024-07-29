package FeastList.meal.domain;

import FeastList.menuItem.MenuItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@Entity
@Setter
@Table(name = "custom_meal")
@PrimaryKeyJoinColumn(name = "id")
public class CustomMeal extends Meal {

    @JsonIgnore
    @Column(name = "client_id")
    private final String clientId;


    @ElementCollection
    @CollectionTable(name = "custom_meal_menu_items",joinColumns = @JoinColumn(name = "meal_id" ,referencedColumnName = "id"))
    @MapKeyJoinColumn(name = "menu_item_id",referencedColumnName = "id" )
    @Column(name = "amount")
    private  final Map<MenuItem,Integer> mealItems;

    @Builder
    public CustomMeal(UUID id, String vendorId, Timestamp dateAdded, HashMap<MenuItem,Integer> mealItems,String clientId) {
        super(id,  vendorId, dateAdded);
        this.mealItems=mealItems;
        this.clientId=clientId;
    }

    @Override
    public double getPrice() {
//        return mealItems
//                .mapToDouble(MealItem::getPrice)
//                .reduce(0,Double::sum);
        return 0;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

}
