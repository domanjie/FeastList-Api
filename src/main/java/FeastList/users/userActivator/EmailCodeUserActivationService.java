package FeastList.users.userActivator;

import FeastList.mailer.MailProps;
import FeastList.mailer.MailerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
@Service
public class EmailCodeUserActivationService implements UserActivationService {
    private final MailerService mailerService;

    private final ActivationRepo activationRepo;
    @Value("${ACTIVATION_CODE_TTL}")
    private long token_duration;
    public EmailCodeUserActivationService(MailerService mailerService, ActivationRepo activationRepo){
        this.mailerService=mailerService;
        this.activationRepo = activationRepo;
    }
    @Override
    @Transactional
    public void begin(String userId)  {
        var ttl=new Date().getTime()+ token_duration*1000;
        var activation=new Activation( userId,UUID.randomUUID().toString().substring(0, 6),ttl);
        var templateAttributes=new HashMap<String,Object>();
        templateAttributes.put("activationCode",activation.ActivationCode());
        var mailProps=new MailProps(
                userId,
                "Activate Your Account",
                "user-activation-template",
                templateAttributes);
        mailerService.sendEmail(mailProps);
        activationRepo.SaveActivation(activation);

    }

    @Override
    @Transactional
    public String confirmActivation(String code) {
        Activation activation= activationRepo.getByActivationCode(code).orElseThrow(()->new ActivationException("Invalid Activation Code") );
        return validateActivation(activation);
    }
    private String  validateActivation(Activation activation){
        var validDuration =new Date(activation.ttl());
        if(new Date().after(validDuration)){
            throw new ActivationException("activation code expired");
        };
        return activation.userId();

    };
}
