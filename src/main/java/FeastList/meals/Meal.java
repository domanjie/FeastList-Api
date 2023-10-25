package FeastList.meals;

import java.util.Objects;

import FeastList.orders.Order;

public class Meal {
	private final long id;
	private final double price;
	private final Order order;
	private final int amount;
	
	public Meal(long id, double price, Order order, int amount) {
		this.id = id;
		this.price = price;
		this.order = order;
		this.amount = amount;
	}
	
	@SuppressWarnings("unused")
	private Meal() {
		id=0;
		price=0;
		order=null;
		amount=0;	
	}
	
	
	public long getId() {
		return id;
	}
	public double getPrice() {
		return price;
	}
	public Order getOrder() {
		return order;
	}
	public int getAmount() {
		return amount;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, id, order, price);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meal other = (Meal) obj;
		return amount == other.amount && id == other.id && Objects.equals(order, other.order)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
	}
	@Override
	public String toString() {
		return "Meals [id=" + id + ", price=" + price + ", order=" + order + ", amount=" + amount + "]";
	}
	
}
