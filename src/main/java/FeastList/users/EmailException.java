package FeastList.users;

public class EmailException extends RuntimeException {
    public EmailException(){
        super("Email error occured");
    }
    public EmailException(String message){
        super(message);
    }
}
