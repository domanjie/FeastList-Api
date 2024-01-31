package FeastList.passwordReset;

import FeastList.mailer.MailerService;
import FeastList.mailer.MailerServiceImpl;
import FeastList.meals.MealService;
import FeastList.passwordReset.dto.PasswordResetDto;
import FeastList.security.AuthenticationService;
import FeastList.users.PasswordException;
import FeastList.users.User;
import FeastList.users.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class PasswordResetServiceImpl implements PasswordResetService{

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final MailerService mailerService;

    private final AuthenticationService authenticationService;
    @Value("${RESET_PASSWORD_CODE_TTL}")
    private  long tokenDuration;
     PasswordResetServiceImpl (PasswordResetRepository passwordResetRepository , UserRepository userRepository,
                                      MailerService mailerService,AuthenticationService authenticationService)
    {
        this.passwordResetRepository=passwordResetRepository;
        this.userRepository=userRepository;
        this.mailerService=mailerService;
        this.authenticationService=authenticationService;
    }
    @Override
    @Transactional
    // still considering this method
    public Map<String, String> confirmResetCode(String resetCode) {

        PasswordReset passwordReset = passwordResetRepository.findByResetCode(resetCode);

        validateResetCode(passwordReset);

        Map<String, String> jsonObject= new HashMap<>();

        jsonObject.put("isValid","true");

        return jsonObject;
    }

    @Override
    @Transactional
    public String resetPassword(PasswordResetDto passwordResetDto) {

        PasswordReset passwordReset =passwordResetRepository.findByResetCode(passwordResetDto.resetCode());

        validateResetCode(passwordReset);

        User user = passwordReset.getUser();

        user.setPassword(passwordResetDto.passwordConfirm());

        userRepository.saveUser(user);

        passwordResetRepository.delete(passwordResetDto.resetCode());

        return "password reset success";

    }

    @Override
    @Transactional
    public String forgetPassword(String email) throws MessagingException {


        User user=userRepository.findById(email);

        String passwordResetCode = UUID.randomUUID().toString().substring(0, 7);

        PasswordReset passwordReset=new PasswordReset(passwordResetCode,getTTLMillis(),user);

        passwordResetRepository.save(passwordReset);

        mailerService.sendPasswordResetEmail(passwordReset.getPasswordResetCode(),email);

        return " password reset code sent to" + email;

    }
    public long getTTLMillis()
    {
        return new Date().getTime()+tokenDuration;
    }
    public void validateResetCode(PasswordReset passwordReset) {
        if(!passwordReset.isValid()){
            passwordResetRepository.delete(passwordReset.getPasswordResetCode());
            throw new PasswordException("invalid password reset code");
        }
    };

}
