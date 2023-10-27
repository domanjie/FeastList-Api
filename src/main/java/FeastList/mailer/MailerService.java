package FeastList.mailer;

import jakarta.mail.MessagingException;

public interface MailerService {
    void sendPasswordResetEmail(String resetCode, String userId) throws MessagingException;
}
