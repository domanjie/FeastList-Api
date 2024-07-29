package FeastList.orders.domain;

import FeastList.meal.domain.Meal;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Order {

	@Id
	@Column(name = "id" ,columnDefinition = "UUID")
	@JdbcType(VarcharJdbcType.class)
	private final UUID id;

	@Column(name = "client_id")
	private final String clientId;


	@Column(name = "delivery_location")
	private final  String deliveryLocation;

	@ElementCollection
	@CollectionTable(name = "orders_meals",joinColumns = {@JoinColumn(name = "order_id",referencedColumnName = "id")})
	@MapKeyJoinColumn(name = "meal_id",referencedColumnName = "id")
	private final Map<Meal,OrderItemDetails> mealAndDetails ;

	@Column(name = "placed_at")
	private final Timestamp placedAt;



    private double getTotalMealCost(){
//        return MealAndQuantity.keySet().stream()
//				.mapToDouble((Meal::getPrice))
//				.reduce(0, Double::sum);
		return 0;
	}
	private double getTotalDeliveryCost (){
		return 0;
	}

	public double getTotalOrderCost(){
		return getTotalDeliveryCost()+getTotalMealCost();
	}
	public OrderStatus getOrderStatus(){
		return null;
	}
}
