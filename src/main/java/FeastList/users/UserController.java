package FeastList.users;

import FeastList.users.dto.PasswordUpgradeDto;
import FeastList.users.dto.RunnerDto;
import FeastList.users.dto.UserDto;
import FeastList.users.dto.VendorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path="/api/v1/rle",produces="application/json")
public  class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public ResponseEntity<List<User>> getVendors(@RequestParam String role) {

        List<User> users = userService.getUsersByRole(role);
        if (!users.isEmpty()) return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/password/changepwd")
    public String upgradePassword(@RequestBody PasswordUpgradeDto passwordUpgradeDto) {
        return userService.updatePassword(passwordUpgradeDto);
    }

    @PostMapping("/client")
    @ResponseStatus(HttpStatus.CREATED)
    public String addProfile(@RequestBody @Valid UserDto userDto) {
        System.out.println(userDto.toString());
        return userService.saveClient(userDto);
    }

    @PostMapping("/profile/activate-user")
    public String profileActivationCode(@RequestBody @NotBlank String activationCode) {
        return userService.activateUser(activationCode);
    }

    @PostMapping(path = "/vendor", consumes = "application/json")
    public String createVendor(@RequestBody VendorDto vendorDto) {
        return userService.saveVendor(vendorDto);
    }

    @PostMapping(path = "/runner", consumes = "application/json")
    public String createRunner(@RequestBody RunnerDto runnerDto) {
        return userService.saveRunner(runnerDto);
    }

}



