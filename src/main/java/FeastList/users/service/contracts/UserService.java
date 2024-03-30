package FeastList.users.service.contracts;

import FeastList.users.domain.User;
import FeastList.users.dto.PasswordChangeDto;
import FeastList.users.dto.VendorDto;

public interface UserService {

    String activateUser(String confirmationCode);

    String updatePassword(PasswordChangeDto passwordChangeDto);



}
