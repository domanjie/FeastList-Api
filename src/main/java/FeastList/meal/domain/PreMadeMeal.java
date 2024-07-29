package FeastList.meal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "pre_made_meal" )
@PrimaryKeyJoinColumn(name ="id" )
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class PreMadeMeal extends Meal {

    @Column(name = "avatar_url")
    private final String avatarUrl;

//    @Column(name = "")
//    private  boolean isAvailable;

    @Column(name = "price" ,columnDefinition = "decimal(11,2)")
    private final double price;

    @Column(name = "meal_name")
    private final String mealName;


    @Builder
    public PreMadeMeal(UUID id, String mealName, String vendorId, Timestamp dateAdded, String avatarUrl, double price) {
        super(id,  vendorId, dateAdded);
        this.avatarUrl=avatarUrl;
        this.price=price;
//        this.isAvailable=isAvailable;
        this.mealName=mealName;

    }


    @Override
    public double getPrice() {
        return this.price;
    }
    public void changeAvailability(boolean isAvailable){
//        this.isAvailable=isAvailable;
    }
    public boolean isAvailable(){
        return false;
//        return this.isAvailable;
    }
}
