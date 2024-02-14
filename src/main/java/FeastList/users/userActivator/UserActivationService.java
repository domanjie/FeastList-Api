package FeastList.users.userActivator;

public interface UserActivationService {
    void begin(String userId) ;
    String confirmActivation(String code );
}
