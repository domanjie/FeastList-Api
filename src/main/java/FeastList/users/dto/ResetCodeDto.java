package FeastList.users.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetCodeDto(
        @NotBlank(message="userId must not be null") String userId ,
        @NotBlank(message="resetCode must not be null") String resetCode) {
}
