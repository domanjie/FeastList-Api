package FeastList.mailer;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface MailerService {
    void sendEmail( MailProps mailProps) ;
    void sendSimpleEmail(String to, String body, String subject);
}
