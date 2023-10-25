package FeastList.users.dto;

import FeastList.users.PasswordException;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public record PasswordUpgradeDto(@NotBlank(message = "password must not be null")  String password,
                                 @NotBlank(message = "confirm password must not be null") String passwordConfirm) {
    public PasswordUpgradeDto{
        if(!Objects.equals(password, passwordConfirm))throw new PasswordException("password and passwordConfirm must be the same");
    }

}
