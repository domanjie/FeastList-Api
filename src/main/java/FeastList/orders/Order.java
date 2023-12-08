package FeastList.orders;

import FeastList.meals.Meal;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Data
@Builder
public class Order {
	
	private final long orderId;

	private final String clientId;

	private final String runnerId;

	private final String vendorId;

	private final  String deliveryLocation;

	private final List<OrderItem> orderItems;

	private final OrderCost orderCost;

	private final Timestamp placedAt;

}
