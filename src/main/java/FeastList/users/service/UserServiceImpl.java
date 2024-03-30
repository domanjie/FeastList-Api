package FeastList.users.service;

import FeastList.security.AuthenticationService;
import FeastList.security.exceptions.UserNotFoundException;
import FeastList.users.UserRepository;
import FeastList.users.domain.Client;
import FeastList.users.domain.User;
import FeastList.users.domain.Vendor;
import FeastList.users.dto.ClientDto;
import FeastList.users.dto.PasswordChangeDto;

import FeastList.users.dto.VendorDto;
import FeastList.users.service.contracts.ClientService;
import FeastList.users.service.contracts.UserService;
import FeastList.users.service.contracts.VendorService;
import FeastList.users.userActivator.UserActivationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService , VendorService, ClientService {
    private final UserRepository userRepository;
    private final UserActivationService userActivationService;

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           UserActivationService userActivationService,AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.userActivationService = userActivationService;
        this.authenticationService=authenticationService;
    }



    @Override
    @Transactional
    public String activateUser(String ActivationCode) {
        String userId   = userActivationService.confirmActivation(ActivationCode);
        Optional<User> optionalUser = userRepository.findById(userId);
        User user=optionalUser.orElseThrow(()->new UserNotFoundException(userId+" not found"));
        user.enable();
        return "user activated successfully";
    }

    @Override
    @Transactional
    public String updatePassword(PasswordChangeDto passwordChangeDto) {

        User user = userRepository.findById(authenticationService.getAuthenticatedUserId()).get();

        user.changePassword(passwordEncoder.encode(passwordChangeDto.passwordConfirm()));

        return "password updated successfully";
    }


    @Override
    @Transactional
    public String addNewClient(ClientDto clientDto) {
        var client= Client.builder()
                .userId(clientDto.userId())
                .password(passwordEncoder.encode(clientDto.passwordConfirm()))
                .build();
        userRepository.save(client);
        return "client saved successfully";
    }

    @Override
    @Transactional
    public String addNewVendor(VendorDto vendorDto) {
        var vendor= Vendor
                .builder()
                .userId(vendorDto.vendorName())
                .phoneNumber(vendorDto.phoneNumber())
                .password(passwordEncoder.encode(vendorDto.passwordConfirm()))
                .location(vendorDto.location())
                .build();
        userRepository.save(vendor);
        return "vendor saved successfully";
    }
}