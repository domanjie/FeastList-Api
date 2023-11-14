package FeastList.passwordReset;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordResetEvent extends ApplicationEvent {
    private final String resetCode;

    private final String userId;
    public PasswordResetEvent(Object source,String resetCode,String userId) {
        super(source);
        this.resetCode=resetCode;
        this.userId=userId;
    }

}
