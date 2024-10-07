package FeastList.orders.domain;

import FeastList.meal.domain.Meal;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Map;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "order_group")
@Builder
public class OrderGroup {

    @EmbeddedId
    private final OrderGroupPk pk;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private  Order order;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private final OrderStatus orderStatus;

    private final double deliveryFee;

    @ElementCollection
    @CollectionTable(name = "order_item",joinColumns ={
            @JoinColumn(name = "order_id",referencedColumnName = "order_id"),
            @JoinColumn(name = "vendor",referencedColumnName = "vendor")
    })
    @MapKeyJoinColumn(name = "meal_id")
    @Column(name = "quantity")
    private final Map<Meal,Integer> orderItems ;

    public double getMealsCost(){
        var costs=new ArrayList<Double>();
        orderItems.forEach((meal, integer) ->costs.add(meal.getPrice()*integer));
        return costs.stream().reduce(0.0,Double::sum);

    };
}
