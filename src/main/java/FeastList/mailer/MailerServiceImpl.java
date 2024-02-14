package FeastList.mailer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
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

    public void SendUserActivationEmail(String activationCode, String userId) throws MessagingException {
        var template="password-reset-template";

        var subject="Password Reset Request";

        var templateAttributes=new HashMap<String,Object>();
        templateAttributes.put("userId",userId);
        templateAttributes.put("resetCode",activationCode);

        sendEmail(userId,template,subject,templateAttributes);

    }
    private   void sendEmail( String to, String template, String subject ,Map<String,Object> templateAttributes) {
        var context=new Context();

        context.setVariables(templateAttributes);

        String htmlBody=templateEngine.process(template,context);

        var from="noreply@FeastList.com";

        MimeMessage mimeMessage=mailSender.createMimeMessage();
        try{var helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");

            helper.setFrom(from);

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(htmlBody,true);
        } catch (MessagingException e) {
            throw new MailerException(e.getMessage());
        }
        mailSender.send(mimeMessage);

    }

    @Override
    public void sendEmail(MailProps mailProps)  {
        sendEmail(mailProps.to(),mailProps.template(), mailProps.subject(), mailProps.templateAttributes());
    }

    @Override
    public void sendSimpleEmail(String to, String body, String subject) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@FeastList.com");
        simpleMailMessage.setText(body);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        mailSender.send(simpleMailMessage);
        System.out.println("email sent");
        System.out.println("email sent");
    }
}
