package FeastList.users.dto;

public record RunnerDto(String userId, String password, String passwordConfirm,
                        String phoneNumber, String city, String state, String Location,
                        String firstName, String lastName, String avatarUrl ,String gender) {

}
