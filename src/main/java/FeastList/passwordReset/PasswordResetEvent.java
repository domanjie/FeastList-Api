package FeastList.passwordReset;

import org.springframework.context.ApplicationEvent;

public class PasswordResetEvent extends ApplicationEvent {
    private final String resetCode;

    private final String userId;
    public PasswordResetEvent(Object source,String resetCode,String userId) {
        super(source);
        this.resetCode=resetCode;
        this.userId=userId;
    }

    public String getResetCode() {
        return resetCode;
    }

    public String getUserId(){
        return userId;
    }
}
