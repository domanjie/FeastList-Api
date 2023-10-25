package FeastList.users;

public class PasswordException extends RuntimeException{
    public PasswordException(){
        super("password error occurred");
    }
    public PasswordException(String msg){
        super(msg);
    }

}
