package FeastList.users;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{
	
	private  String email;
	private  Timestamp created_at=new Timestamp(new Date().getTime());
	private  String identity_number;
	private  String password;
	private  char gender;
	private  String roles;
	private  String firstName;
	private  String Last_name;
	private  String phoneNumber;
	private  String location;
	private  String state;
	private  String city;
	private  String street;
	private  String zip;
	private  List<Roles>  authorities;
	private boolean isEnabled;
	 
	public User(String email, Timestamp created_at, String identity_number, String password, char gender, String roles,
			String firstName, String last_name, String phoneNumber, String location, String state, String city,
			String street, String zip, List<Roles> authorities, boolean isEnabled) {
		this.email = email;
		this.created_at = created_at;
		this.identity_number = identity_number;
		this.password = password;
		this.gender = gender;
		this.roles = roles;
		this.firstName = firstName;
		Last_name = last_name;
		this.phoneNumber = phoneNumber;
		this.location = location;
		this.state = state;
		this.city = city;
		this.street = street;
		this.zip = zip;
		this.authorities = authorities;
		this.isEnabled = isEnabled;
	}
	@SuppressWarnings("unused")
	private User() {}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> newList=new ArrayList<>();
		authorities.forEach(role->newList.add(new SimpleGrantedAuthority(role.toString())));
		return newList;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		return Objects.hash(Last_name, authorities, city, created_at, email, firstName, gender, identity_number,
				isEnabled, location, password, phoneNumber, roles, state, street, zip);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(Last_name, other.Last_name) && Objects.equals(authorities, other.authorities)
				&& Objects.equals(city, other.city) && Objects.equals(created_at, other.created_at)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& gender == other.gender && Objects.equals(identity_number, other.identity_number)
				&& isEnabled == other.isEnabled && Objects.equals(location, other.location)
				&& Objects.equals(password, other.password) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(roles, other.roles) && Objects.equals(state, other.state)
				&& Objects.equals(street, other.street) && Objects.equals(zip, other.zip);
	}
	@Override
	public String toString() {
		return "Users [email=" + email + ", created_at=" + created_at + ", identity_number=" + identity_number
				+ ", password=" + password + ", gender=" + gender + ", roles=" + roles + ", firstName=" + firstName
				+ ", Last_name=" + Last_name + ", phoneNumber=" + phoneNumber + ", location=" + location + ", state="
				+ state + ", city=" + city + ", street=" + street + ", zip=" + zip + ", authorities=" + authorities
				+ ", isEnabled=" + isEnabled + "]";
	}
	public enum Roles{
		ORDER_RUNNER,
		ORDER_ISSUER
	};
		
	
}
