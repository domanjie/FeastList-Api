package FeastList.users;

import FeastList.users.dto.PasswordResetDto;
import FeastList.users.dto.PasswordUpgradeDto;
import FeastList.users.dto.ResetCodeDto;
import FeastList.users.dto.userDto;
import FeastList.users.vendors.Vendor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/user",produces="application/json")
public  class UserController {
    private final UserService userService;


    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("client/{id}")
    public User getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }
    @GetMapping("/password/reset/{email.code}")
    public String  forgetPassword(@PathVariable @NotBlank String email){
        return userService.forgetPassword(email);
    }
    @PostMapping("")
    public String upgradePassword(@RequestBody PasswordUpgradeDto passwordUpgradeDto){
        return userService.updatePassword(passwordUpgradeDto);
    }
    @PostMapping
    public String addProfile(@RequestBody @Valid userDto userdto){
        return userService.createNewUser(userdto);
    }
    @PostMapping("/profile/activation")
    public String profileActivationCode(@RequestBody @NotBlank String activationCode){
        return userService.activateUser(activationCode);
    }
    @PostMapping("/password/reset")
    public String  resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        return userService.resetPassword(passwordResetDto);
    }
    @PostMapping("/password/code")
    public String confirmPasswordRestCode(@RequestBody @Valid ResetCodeDto resetCode)
    {
        return userService.confirmResetCode(resetCode);
    }
}

