package FeastList.users;

import FeastList.security.exceptions.UserNotFoundException;
import FeastList.users.dto.PasswordUpgradeDto;
import FeastList.users.dto.RunnerDto;
import FeastList.users.dto.UserDto;
import FeastList.users.dto.VendorDto;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public String saveClient(UserDto userDto) {
    	User user=User
    			.builder()
    			.userId(userDto.userId())
    			.password(passwordEncoder.encode(userDto.passwordConfirm()))
    			.gender(Gender.valueOf(userDto.gender()))
                .isEnabled(false)
                .role(Role.CLIENT)
    			.build();

        userRepository.saveUser(user);

        return "user created successfully";

    }

    @Override
    @Transactional
    public String activateUser(String activationCode) {

        User user = userRepository.findByActivationCode(activationCode);

        user.setEnabled(true);

        userRepository.updateUser(user);

        return "user activated successfully";
    }

    @Override
    public String updatePassword(PasswordUpgradeDto passwordUpgradeDto) {

        String identity = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(identity).orElseThrow(
                ()->new UserNotFoundException("user:" +identity+" not found"));

        user.setPassword(passwordUpgradeDto.passwordConfirm());

        userRepository.updateUser(user);

        return "password updated successfully";
    }



    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user:"+userId+" not-found"));
    }

	@Override
	public String saveVendor(VendorDto vendorDto) {
		User user=User.builder()
                .userId(vendorDto.userId())
                .location(vendorDto.location())
                .avatarUrl(vendorDto.avatarUrl())
                .role(Role.VENDOR)
                .vendorName(vendorDto.vendorName())
                .phoneNumber(vendorDto.phoneNumber())
                .password(vendorDto.passwordConfirm())
                .isEnabled(false)
                .dateJoined(new Timestamp(new Date().getTime()))
                .build();
        return userRepository.saveUser(user);
	}
    @Override
    public String saveRunner(RunnerDto runnerDto) {
        User user=User.builder()
                .gender(Gender.valueOf(runnerDto.gender()))
                .userId(runnerDto.userId())
                .password(runnerDto.passwordConfirm())
                .avatarUrl(runnerDto.avatarUrl())
                .location(runnerDto.Location())
                .firstName(runnerDto.firstName())
                .lastName(runnerDto.lastName())
                .isEnabled(false)
                .role(Role.RUNNER)
                .dateJoined(new Timestamp(new Date().getTime()))
                .build();
       return userRepository.saveUser(user);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        Role userRole=Role.valueOf(role);
       return userRepository.getUsersByRole(userRole);
    }
}