package intermediary;

public class WrongTurnException extends Exception{
    public WrongTurnException() {
        super("Error: it is not your turn!");
    }
}