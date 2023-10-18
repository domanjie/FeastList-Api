package FeastList.users;

import FeastList.users.dto.PasswordResetDto;
import FeastList.users.dto.PasswordUpgradeDto;
import FeastList.users.dto.ResetCodeDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createNewUser(User user) {

        userRepository.saveUser(user);

        return "user created successfully";

    }

    @Override
    @Transactional
    public String activateUser(String activationCode) {

        User user = userRepository.findByActivationCode(activationCode);

        user.setEnabled(true);

        userRepository.saveUser(user);

        return "user activated successfully";
    }

    @Override
    @Transactional
    public String resetPassword(PasswordResetDto passwordResetDto) {


        User user = userRepository.findById(passwordResetDto.userIdentity());

        if (!(passwordResetDto.resetCode().equals(user.getPasswordResetCode())))
            throw new PasswordException("invalid password reset code");

        user.setPasswordResetCode(null);

        user.setPassword(passwordResetDto.passwordConfirm());

        userRepository.saveUser(user);

        return "password reset success";

    }

    @Override
    public String updatePassword(PasswordUpgradeDto passwordUpgradeDto) {
        String identity = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(identity);

        user.setPassword(passwordUpgradeDto.passwordConfirm());

        userRepository.saveUser(user);

        return "password updated successfully";
    }

    @Override
    @Transactional
    public String confirmResetCode(ResetCodeDto resetCodeDto) {

        String userId = userRepository.getIdentityByResetCode(resetCodeDto.resetCode());

        if (!(resetCodeDto.userId().equals(userId))) throw new EmailException();

        return userId;

    }

    @Override
    public User getUserById(String userId) {

        return userRepository.findById(userId);

    }

    @Override
    @Transactional
    public String forgetPassword(String email) {

        User user = userRepository.findById(email);

        String passwordResetCode = UUID.randomUUID().toString().substring(0, 7);

        user.setPasswordResetCode(passwordResetCode);

        sendEmail(email, passwordResetCode);

        userRepository.saveUser(user);

        return " password reset code sent to" + email;

    }
}