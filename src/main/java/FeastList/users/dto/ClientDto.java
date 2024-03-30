package FeastList.users.dto;

import jakarta.validation.constraints.Email;

public record ClientDto(@Email String userId, String password, String passwordConfirm ) {
}
