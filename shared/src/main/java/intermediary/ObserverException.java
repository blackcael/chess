package intermediary;

public class ObserverException extends Exception{
    public ObserverException() {
        super("Error: cannot perform this action as Observer");
    }
}