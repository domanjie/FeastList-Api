package FeastList.orders.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "orders")
@Getter
public class Order {

	@Id
	@Column(name = "id" ,columnDefinition = "UUID")
	@JdbcType(VarcharJdbcType.class)
	@Getter
	private final UUID id;

	@Column(name = "client_id")
	private final String clientId;

	@Column(name = "delivery_location")
	private final  String deliveryLocation;

	@OneToMany(mappedBy = "order",cascade =CascadeType.ALL ,orphanRemoval = true)
	private final List<OrderGroup> orderGroups;

	@Column(name = "placed_at")
	private final Timestamp placedAt;

	public void addOrderGroup(OrderGroup orderGroup){
		orderGroups.add(orderGroup);
		orderGroup.setOrder(this);
	};
    private double getTotalMealCost(){
		double totalCost=0;
		for(OrderGroup orderGroup:orderGroups){
			totalCost+=orderGroup.getMealsCost();
		}
		return totalCost;
	}

	public double getTotalDeliveryCost (){

        return orderGroups.stream().map(OrderGroup::getDeliveryFee).reduce(0.0, Double::sum);
	}

	public double getTotalOrderCost(){
		return getTotalDeliveryCost()+getTotalMealCost();
	}

	public OrderStatus getOrderStatus(){
		return null;
	}

}
