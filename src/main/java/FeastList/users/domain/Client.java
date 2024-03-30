package FeastList.users.domain;

import FeastList.users.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@NoArgsConstructor
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends User{
    @Transient
    private final Role role=Role.CLIENT;
    @Builder
    public Client(String userId, String password, Timestamp dateJoined, String phoneNumber,boolean isEnabled, String avatarUrl, Gender gender) {
        super(userId, password, phoneNumber,  isEnabled, avatarUrl, dateJoined);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }
}
