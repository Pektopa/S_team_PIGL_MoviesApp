package moviesapp.model.exceptions;

public class NoTitleException extends Exception{

    public NoTitleException() {
        super("\n| Title is required if you want to use this search mode.");
    }
}
