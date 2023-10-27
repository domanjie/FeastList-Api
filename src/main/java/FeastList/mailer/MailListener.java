package FeastList.mailer;

import FeastList.passwordReset.PasswordResetEvent;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MailListener {
    private final MailerService mailerService;
    public MailListener(MailerService mailerService){
        this.mailerService=mailerService;
    }
    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent passwordResetEvent) throws MessagingException {
        mailerService.sendPasswordResetEmail( passwordResetEvent.getResetCode(), passwordResetEvent.getUserId());
    }

    @EventListener
    public void handleUserActivationEvent()
    {

    }

}
