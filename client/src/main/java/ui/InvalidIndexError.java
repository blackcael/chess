package ui;

public class InvalidIndexError extends Exception{
    public InvalidIndexError() {
        super("Error: invalid index");
    }
}
