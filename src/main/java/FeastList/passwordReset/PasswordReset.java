package FeastList.passwordReset;

import FeastList.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PasswordReset {
    private  String passwordResetCode;

    private  long TTL;

    private  User user;

    public boolean isValid() {
        return new Date().before(new Date(TTL));
    }
}
