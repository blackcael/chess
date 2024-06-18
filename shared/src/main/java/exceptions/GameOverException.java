package exceptions;

public class GameOverException extends Exception{
    public GameOverException(){super("Error: game is already over!");}
}
