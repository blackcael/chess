package intermediary;

public class InvalidAuthException extends Exception{
    public  InvalidAuthException() {
        super("Error: unauthorized");
    }
}
