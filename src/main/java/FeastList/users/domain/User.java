package FeastList.users.domain;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements UserDetails{

	@Id
	@Column(name = "user_id")
	private final  String userId;

	@Column(name ="password")
	private  String password;

	@Column(name="phone_number")
	private final String phoneNumber;

	@Column(name = "is_enabled")
	private   boolean isEnabled;

	@Column(name = "avatar_url")
	private  final String avatarUrl;

	@Column(name="date_joined")
	private final Timestamp dateJoined;



	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();
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

	public void changePassword(String newPassword) {
		this.password=newPassword;
	}

	public void enable() {
		this.isEnabled=true;
	}
	public void disable(){
		this.isEnabled=false;
	}

}
