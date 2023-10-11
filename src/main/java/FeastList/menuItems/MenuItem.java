package FeastList.menuItems;

import java.util.Objects;

import FeastList.restaurants.Restaurant;

public class MenuItem {
	private final long  id;
	private final String name;
	private final  double price_per_portion;
	private final Restaurant restaurant;
	private final String avatar_url;
	
	public MenuItem(long id, String name, double price_per_portion, Restaurant restaurant, String avatar_url) {
		this.id = id;
		this.name = name;
		this.price_per_portion = price_per_portion;
		this.restaurant = restaurant;
		this.avatar_url = avatar_url;
	}

	@SuppressWarnings("unused")
	private MenuItem() {
		id=0;
		name=null;
		price_per_portion=0;
		restaurant =null;
		avatar_url=null;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getPrice_per_portion() {
		return price_per_portion;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	@Override
	public int hashCode() {
		return Objects.hash(avatar_url, id, name, price_per_portion, restaurant);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		return Objects.equals(avatar_url, other.avatar_url) && id == other.id && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price_per_portion) == Double.doubleToLongBits(other.price_per_portion)
				&& restaurant == other.restaurant;
	}
	@Override
	public String toString() {
		return "FoodItems [id=" + id + ", name=" + name + ", price_per_portion=" + price_per_portion + ", restuarant="
				+ restaurant + ", avatar_url=" + avatar_url + "]";
	}
}
