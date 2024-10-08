package FeastList.users.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "vendor_id")
@Table(name ="vendors")
@NoArgsConstructor(force = true)
public class Vendor extends User{
    @Column(name = "vendor_username")
    private final String username;

    @Column(name= "header_photo")
    private final String headerPhoto;

    @Column(name = "location")
    private final String location;


    @Transient
    private final Role role =Role.VENDOR;
    @Builder
    public Vendor(String userId, String password, Timestamp dateJoined, String phoneNumber, boolean isEnabled,
                  String avatarUrl, String vendorUsername,String header_photo,String location) {
        super(userId, password, phoneNumber,  isEnabled, avatarUrl,dateJoined);
        this.username=vendorUsername;
        this.headerPhoto=header_photo;
        this.location=location;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }
}
