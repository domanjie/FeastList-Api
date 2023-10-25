package FeastList.users.dto;

import FeastList.users.Gender;
import FeastList.users.Role;

import java.sql.Timestamp;

public record VendorDto(String userId, String password ,String passwordConfirm, String phoneNumber,
                        String vendorName,String avatarUrl,String location,String state,String city,String street) {
}
