package FeastList.users;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class User implements UserDetails{
	
	private  String userId;
	private  String password;
	private  Timestamp dateJoined;
	private  String phoneNumber;
	private  String location;
	private  String state;
	private  String city;
	private  String street;
	private  String zip;
	private String PasswordResetCode;
	private boolean isEnabled;
	public User(String userId, String password,String phoneNumber, String location, String state,
				String city,String street, String zip,  boolean isEnabled) {
		this.userId = userId;
		this.dateJoined =new Timestamp(new Date().getTime()) ;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.location = location;
		this.state = state;
		this.city = city;
		this.street = street;
		this.zip = zip;
		this.isEnabled = isEnabled;
	}
	@SuppressWarnings("unused")
	public User() {}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPasswordResetCode() {
		return PasswordResetCode;
	}

	public void setPasswordResetCode(String passwordResetCode) {
		PasswordResetCode = passwordResetCode;
	}
	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return userId;
	}
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
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
	public boolean isCredentialsNonExpired() {return false;}

}
