package moviesapp.model.exceptions;

public class NoPreviousPageException extends Exception {

    public NoPreviousPageException() {
        super("\n| There is no previous page.");
    }
}
