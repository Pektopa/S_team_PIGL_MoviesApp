package moviesapp.model.exceptions;

public class NotAPositiveIntegerException extends Exception{
    public NotAPositiveIntegerException() {
        super("\n| Enter a value that is a positive integer to continue.");
    }
}
