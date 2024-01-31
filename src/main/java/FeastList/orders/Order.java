package FeastList.orders;

import FeastList.meals.Meal;
import FeastList.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Data
@AllArgsConstructor
@Builder
public class Order {

	private final long orderId;

	private final String clientId;
	@Setter
	private  String runnerId;

	private final String vendorId;

	private final  String deliveryLocation;

	private final List<OrderItem> orderItems;

	private final Timestamp placedAt;

	private OrderStatus orderStatus;

	private double deliveryCost;

	private final PaymentMethod paymentMethod;


	public double getMealsCost(){
        return orderItems.stream()
				.mapToDouble((OrderItem::getOrderItemPrice))
				.reduce(0, Double::sum);
	}
	public double getTotalCost(){
		return getMealsCost()+deliveryCost;
	}
}
