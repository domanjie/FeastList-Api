//package FeastList.users.domain;
//
//import FeastList.users.Gender;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.sql.Timestamp;
//import java.util.Collection;
//import java.util.Collections;
//
//public class Courier extends User{
//    private final String firstName;
//    private final String lastName;
//    private final Gender gender;
//
//    private final Role role=Role.RUNNER;
//    public Courier(String userId, String password, Timestamp dateJoined, String phoneNumber, String location, String zipCode, Role role, boolean isEnabled, String avatarUrl, String vendorName, String firstName, String lastName, Gender gender) {
//        super(userId, password, dateJoined, phoneNumber, location, zipCode, role, isEnabled, avatarUrl);
//        this.firstName=firstName;
//        this.lastName=lastName;
//        this.gender=gender;
//
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
//    }
//}
