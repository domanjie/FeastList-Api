package FeastList.mailer;

import lombok.Builder;

import java.util.HashMap;
@Builder
public record MailProps(String to, String subject,String template, HashMap<String ,Object> templateAttributes) {
}
