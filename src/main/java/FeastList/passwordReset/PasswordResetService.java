package FeastList.passwordReset;


import FeastList.passwordReset.dto.PasswordResetDto;
import jakarta.mail.MessagingException;

import java.util.Map;


public interface PasswordResetService {
    Map<String,String> confirmResetCode(String resetCode);

    String resetPassword(PasswordResetDto passwordResetDto);

    String forgetPassword(String userId) throws MessagingException;
}
