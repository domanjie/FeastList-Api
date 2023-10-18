package FeastList.users.runner;

import FeastList.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

public class Runner extends User {
    private String name;

    private String avatarUrl;

    private Order currentOrder;

    private String role;
    public Runner(String userId,  String password, String phoneNumber, String location, String state, String city, String street, String zip, boolean isEnabled,String avatar_url,String name) {
        super(userId,  password, phoneNumber, location, state, city, street, zip, isEnabled);
        this.name=name;
        this.avatar_url=avatar_url;

    }
    public Runner(){}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    public Order AcceptOrder(){}

    public Order getCurrentOrder(){}

    public Order  completeOrder(){}

    public List<Order> getOrderHistory(){}
}
