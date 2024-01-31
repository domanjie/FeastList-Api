package FeastList.passwordReset;


import FeastList.passwordReset.dto.PasswordResetDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PasswordResetController {
    private final PasswordResetService passwordResetService;
    public PasswordResetController(PasswordResetService passwordResetService){
        this.passwordResetService=passwordResetService;
    }
    @PostMapping("/password/resetpwd")
    public String  resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        return passwordResetService.resetPassword(passwordResetDto);
    }

    @PostMapping("/password/resetcode")
    public Map<String,String> confirmPasswordRestCode(@RequestBody @NotBlank String resetCode)
    {
        return passwordResetService.confirmResetCode(resetCode);
    }
    @GetMapping("/password/reset/{userId}")
    public String  forgetPassword(@PathVariable("userId") @NotBlank String email) throws MessagingException {
        return passwordResetService.forgetPassword(email);
    }
}
