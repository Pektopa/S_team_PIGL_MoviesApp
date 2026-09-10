package moviesapp.model.exceptions;

public class NoNextPageException extends Exception {

    public NoNextPageException() {
        super("\n| There is no next page.");
    }
}
