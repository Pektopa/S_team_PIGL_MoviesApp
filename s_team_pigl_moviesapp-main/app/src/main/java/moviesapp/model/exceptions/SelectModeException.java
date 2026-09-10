package moviesapp.model.exceptions;

public class SelectModeException extends Exception {

    public SelectModeException(){
        super("\n| Select mode method is not working anymore.");
    }
}
