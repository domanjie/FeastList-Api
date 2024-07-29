package FeastList.meal;

public class MealException extends RuntimeException {
    public MealException(){
        super("no such meal found");
    };
    public MealException(String msg){
        super(msg);
    }
}
