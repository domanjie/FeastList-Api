package FeastList.mailer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;


@Component
public class MailerServiceImpl implements MailerService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    public MailerServiceImpl (JavaMailSender mailSender ,SpringTemplateEngine   templateEngine)
    {
        this.mailSender=mailSender;

        this.templateEngine=templateEngine;

    }

    @Override
    public void sendPasswordResetEmail(String resetCode, String userId) throws MessagingException {

        var template="password-reset-template";

        var subject="Password Reset Request";

        var templateAttributes=new HashMap<String,Object>();
        templateAttributes.put("userId",userId);
        templateAttributes.put("resetCode",resetCode);

        sendEmail(userId,template,subject,templateAttributes);

    }
    private void sendEmail( String to, String template, String subject ,Map<String,Object> templateAttributes) throws MessagingException {
        var context=new Context();

        context.setVariables(templateAttributes);

        String htmlBody=templateEngine.process(template,context);

        var from="noreply@FeastList.com";

        MimeMessage mimeMessage=mailSender.createMimeMessage();

        var helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");

        helper.setFrom(from);

        helper.setTo(to);

        helper.setSubject(subject);

        helper.setText(htmlBody,true);

        mailSender.send(mimeMessage);

    }
}
