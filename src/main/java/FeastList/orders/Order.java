package FeastList.orders;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Order {
	
	private final long id; 
	private final  String location;
	private final String order_issuer;
	private final String runner;
	private final long tray;
	private final Timestamp created_at=new Timestamp(new Date().getTime());
	private final OrderStatus orderStatus;
	
	public Order(long id, String location, String order_issuer, String runner, long tray, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.location = location;
		this.order_issuer = order_issuer;
		this.runner = runner;
		this.tray = tray;
		this.orderStatus = orderStatus;
	}
	@SuppressWarnings("unused")
	private Order() 
	{
		id=0;
		location=null;
		order_issuer=null;
		runner=null;
		tray=0;
		orderStatus=null;
	}
	

	public long getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public String getOrder_issuer() {
		return order_issuer;
	}

	public String getRunner() {
		return runner;
	}

	public long getTray() {
		return tray;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public enum OrderStatus {
		PENDING_RUNNER,PENDING_DELIVERY,COMPLETED,CANCELLED
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", location=" + location + ", order_issuer=" + order_issuer + ", runner=" + runner
				+ ", tray=" + tray + ", created_at=" + created_at + ", orderStatus=" + orderStatus + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(created_at, id, location, orderStatus, order_issuer, runner, tray);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(created_at, other.created_at) && id == other.id
				&& Objects.equals(location, other.location) && orderStatus == other.orderStatus
				&& Objects.equals(order_issuer, other.order_issuer) && Objects.equals(runner, other.runner)
				&& tray == other.tray;
	}

	
	
}
