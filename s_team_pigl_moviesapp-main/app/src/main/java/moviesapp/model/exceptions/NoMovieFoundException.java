package moviesapp.model.exceptions;

public class NoMovieFoundException extends Exception {

    public NoMovieFoundException() {
        super("\n| No movie found.");
    }
}
