package FeastList.passwordReset;

import FeastList.users.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
public class PasswordReset {
    private final String passwordResetCode;

    private final long TTL;

    private  final User user;

    public boolean isValid() {
        return new Date().before(new Date(TTL));
    }
}
