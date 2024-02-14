package FeastList.users;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User implements UserDetails{
	
	private  String userId;
	private  String password;
	private  Timestamp dateJoined;
	private  String phoneNumber;
	private  String location;
	private  String zipCode;
	private Role role;
	private boolean isEnabled;
	private String avatarUrl;
	private String vendorName;
	private String firstName;
	private String lastName;
	private Gender gender;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
	}
	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled(){
		return true;
	}
}
