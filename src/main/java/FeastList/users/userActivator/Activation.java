package FeastList.users.userActivator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "activation")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@EqualsAndHashCode
@ToString
@Getter
public class Activation {
    @Id
    @Column(name = "user_id")
    private final String userId;

    @Column(name = "activation_code")
    private final String activationCode;

    @Column(name = "ttl")
    private final long ttl;


    public boolean isValid(){
        var validDuration =new Date(this.ttl);
        return !(new Date().after(validDuration));
    }


}
