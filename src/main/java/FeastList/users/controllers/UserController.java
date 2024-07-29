package FeastList.users.controllers;

import FeastList.users.dto.ClientDto;
import FeastList.users.dto.MiniVendorProjection;
import FeastList.users.dto.VendorDto;
import FeastList.users.service.contracts.ClientService;
import FeastList.users.service.contracts.UserService;
import FeastList.users.dto.PasswordChangeDto;
import FeastList.users.service.contracts.VendorService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/rle",produces="application/json")
public  class UserController {
    private final UserService userService;
    private final ClientService clientService;

    private final VendorService vendorService;

    public UserController(UserService userService, ClientService clientService,VendorService vendorService ) {
        this.userService = userService;
        this.clientService=clientService;
        this.vendorService=vendorService;
    }

    @PostMapping("/password/changepwd")
    public String upgradePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        return userService.updatePassword(passwordChangeDto);
    }


    @PostMapping("/profile/activate-user")
    public String profileActivationCode(@RequestBody @NotBlank String activationCode) {
        return userService.activateUser(activationCode);
    }
    @PostMapping(path = "/client" ,consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody ClientDto clientDto) {
        return clientService.addNewClient(clientDto);
    }

    @PostMapping(path = "/vendor", consumes = "application/json")
    public String createVendor(@RequestBody VendorDto vendorDto) {
        return vendorService.addNewVendor(vendorDto);
    }

    @GetMapping(path="/vendors")
    public List<MiniVendorProjection> fetchVendors(@RequestParam("sort") String sort){
        return vendorService.fetchVendors(sort);
    }
}



