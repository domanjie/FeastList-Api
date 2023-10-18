package FeastList.users.dto;

import FeastList.users.PasswordException;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public record PasswordResetDto(@NotBlank(message ="userid must not be blank") String userIdentity,
                               @NotBlank(message=" password must not be blank") String password,
                               @NotBlank(message = "passwordConfirm must not blank") String passwordConfirm,
                               @NotBlank(message = "reset code must be present") String resetCode) {

    public PasswordResetDto{
            if(!Objects.equals(password, passwordConfirm))throw new PasswordException("password and passwordConfirm must be the same");
        }
}
