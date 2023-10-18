package FeastList.users.purchaser;

import FeastList.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class Client extends User {
    private String role;
    public Client(String userId, String password, String phoneNumber, String location, String state, String city,
                  String street, String zip, boolean isEnabled) {
        super(userId, password, phoneNumber, location, state, city, street, zip, isEnabled);
    }
    public Client(){
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

}
