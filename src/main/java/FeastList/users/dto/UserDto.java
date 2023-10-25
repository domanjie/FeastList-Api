package FeastList.users.dto;

import jakarta.validation.constraints.Email;

public record UserDto(@Email String userId, String password, String passwordConfirm, String  gender ) {
}
