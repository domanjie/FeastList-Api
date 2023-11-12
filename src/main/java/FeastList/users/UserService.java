package FeastList.users;

import FeastList.users.dto.PasswordUpgradeDto;
import FeastList.users.dto.RunnerDto;
import FeastList.users.dto.UserDto;
import FeastList.users.dto.VendorDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    String activateUser(String confirmationCode);


    User getUserById(String userId);

    String updatePassword(PasswordUpgradeDto passwordUpgradeDto);

	String saveVendor(VendorDto vendorDto);


	String saveClient(@Valid UserDto userDto);

	String saveRunner(RunnerDto runnerDto);


    List<User> getUsersByRole(String role);
}
