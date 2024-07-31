package FeastList.tray.dto;


public class TrayItemNotFoundException extends RuntimeException{
    public TrayItemNotFoundException(){}

    public TrayItemNotFoundException(String msg){
        super(msg);
    }
}
