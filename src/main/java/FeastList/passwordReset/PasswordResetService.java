package FeastList.passwordReset;


import FeastList.passwordReset.dto.PasswordResetDto;

import java.util.Map;


public interface PasswordResetService {
    Map<String,String> confirmResetCode(String resetCode);

    String resetPassword(PasswordResetDto passwordResetDto);

    String forgetPassword(String email);
}
