package FeastList.users.vendors;

import FeastList.users.User;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class Vendor extends User {
	private  String avatarUrl;
	private  String vendorName;

	public Vendor(String userId, Timestamp dateJoined, String password, String phoneNumber, String location, String state, String city, String street, String zip, boolean isEnabled,String vendorName,String avatarUrl) {
		super(userId, dateJoined, password, phoneNumber, location, state, city, street, zip, isEnabled);
		this.vendorName=vendorName;
		this.avatarUrl = avatarUrl;
	}
	public Vendor() {
	}

	@Overrided
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
