package moviesapp.model.exceptions;

public class NotValidYearsException extends Exception{

    public NotValidYearsException() {
        super("\n| Please enter a minimum year that is less than or equal to the maximum year.");
    }
}
