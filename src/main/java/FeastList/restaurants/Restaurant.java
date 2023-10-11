package FeastList.restaurants;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Restaurant {
	private final int id;
	private final String name;
	private final String email;
	private final String avatar_url;
	private final String location;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private Timestamp created_at=new Timestamp(new Date().getTime());
	
	public Restaurant(int id, String name, String email, String avatar_url, String location, String street,
			String city, String state, String zip, Timestamp created_at) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.avatar_url = avatar_url;
		this.location = location;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.created_at = created_at;
	}
	private Restaurant() 
	{
		id=0;
		name=null;
		email=null;
		avatar_url=null;
		location=null;
		street=null;
		city=null;
		state=null;
		zip=null;
		
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public String getLocation() {
		return location;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	@Override
	public int hashCode() {
		return Objects.hash(avatar_url, city, created_at, email, id, location, name, state, street, zip);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		return Objects.equals(avatar_url, other.avatar_url) && Objects.equals(city, other.city)
				&& Objects.equals(created_at, other.created_at) && Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(location, other.location) && Objects.equals(name, other.name)
				&& Objects.equals(state, other.state) && Objects.equals(street, other.street)
				&& Objects.equals(zip, other.zip);
	}
	@Override
	public String toString() {
		return "Restaurants [id=" + id + ", name=" + name + ", email=" + email + ", avatar_url=" + avatar_url
				+ ", location=" + location + ", street=" + street + ", city=" + city + ", state=" + state + ", zip="
				+ zip + ", created_at=" + created_at + "]";
	}
	
}
