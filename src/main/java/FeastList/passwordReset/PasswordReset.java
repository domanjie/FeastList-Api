package FeastList.passwordReset;

import FeastList.users.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "password_reset")
@NoArgsConstructor(access = AccessLevel.PACKAGE  ,force = true)
@AllArgsConstructor
@Getter
public class PasswordReset {

    @Id
    @Column(name = "user_id")
    private  final String userId;

    @Column(name="reset_code" ,columnDefinition = "UUID")
    private final UUID passwordResetCode;

    @Column(name="ttl")
    private final long ttl;


    public boolean isValid() {
        return new Date().before(new Date(ttl));
    }
}
