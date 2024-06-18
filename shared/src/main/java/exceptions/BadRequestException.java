package exceptions;

/**
 * Indicates there was an error connecting to the database
 */
public class BadRequestException extends Exception{
    public BadRequestException() {
        super("Error: bad request");
    }
}