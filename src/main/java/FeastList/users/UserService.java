package FeastList.users;

import FeastList.users.dto.PasswordResetDto;
import FeastList.users.dto.PasswordUpgradeDto;
import FeastList.users.dto.ResetCodeDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

public interface UserService {
    String createNewUser(User user);

    String activateUser(String confirmationCode);

    String resetPassword(PasswordResetDto passwordResetDto);

    String confirmResetCode(ResetCodeDto resetCode);

    User getUserById(String userId);

    String forgetPassword(String email);

    String updatePassword(PasswordUpgradeDto passwordUpgradeDto);


}
