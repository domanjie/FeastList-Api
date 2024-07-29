package FeastList.passwordReset;

import FeastList.mailer.MailProps;
import FeastList.mailer.MailerService;
import FeastList.passwordReset.dto.PasswordResetDto;
import FeastList.security.AuthenticationService;
import FeastList.security.exceptions.UserNotFoundException;
import FeastList.users.PasswordException;
import FeastList.users.domain.User;
import FeastList.users.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PasswordResetServiceImpl implements PasswordResetService{

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final MailerService mailerService;

    private final PasswordEncoder passwordEncoder;
    @Value("${RESET_PASSWORD_CODE_TTL}")
    private  long tokenDuration;
     PasswordResetServiceImpl (PasswordResetRepository passwordResetRepository , UserRepository userRepository,
                                      MailerService mailerService,PasswordEncoder passwordEncoder)
    {
        this.passwordResetRepository=passwordResetRepository;
        this.userRepository=userRepository;
        this.mailerService=mailerService;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    @Transactional
    // still considering this method
    public Map<String, String> confirmResetCode(String resetCode) {


        PasswordReset passwordReset =passwordResetRepository
                .findByPasswordResetCode(resetCode)
                .orElseThrow(()->new PasswordResetException("invalid Reset Code"));


        validateResetCode(passwordReset);

        Map<String, String> jsonObject= new HashMap<>();

        jsonObject.put("isValid","true");

        return jsonObject;
    }

    @Override
    @Transactional
    public String resetPassword(PasswordResetDto passwordResetDto) {

        PasswordReset passwordReset =passwordResetRepository
                .findByPasswordResetCode(passwordResetDto.resetCode())
                .orElseThrow(()->new PasswordResetException("invalid Reset Code"));

        validateResetCode(passwordReset);

        User user = userRepository.findById(passwordReset.getUserId())
                .orElseThrow(()->new PasswordResetException("Associated user does not exist"));

        user.changePassword(passwordEncoder.encode(passwordResetDto.passwordConfirm()));

        passwordResetRepository.deleteById(passwordReset.getUserId());

        return "password reset success";

    }

    @Override
    @Transactional
    public String forgetPassword(String email) throws MessagingException {


        var secureRandomPasswordResetCode = UUID.randomUUID();

        PasswordReset passwordReset=new PasswordReset(email,secureRandomPasswordResetCode,getTTLMillis());

        passwordResetRepository.save(passwordReset);

        var templateAttributes=new HashMap<String,Object>();
        templateAttributes.put("userId",email);
        templateAttributes.put("resetCode",passwordReset.getPasswordResetCode());
        MailProps mailProps=MailProps
                .builder()
                .to(email)
                .subject("password-reset-template")
                .templateAttributes(templateAttributes).build();

        mailerService.sendEmail(mailProps);
        return " password reset code sent to" + email;

    }
    public long getTTLMillis()
    {
        return new Date().getTime()+(tokenDuration*1000);
    }
    public void validateResetCode(PasswordReset passwordReset) {
        if(!passwordReset.isValid()){
            passwordResetRepository.deleteById(passwordReset.getUserId());
            throw new PasswordException("invalid password reset code");
        }
    };

}
