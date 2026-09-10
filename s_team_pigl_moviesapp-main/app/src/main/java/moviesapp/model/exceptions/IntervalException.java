package moviesapp.model.exceptions;

public class IntervalException extends Exception{

    public IntervalException(){
        super("\n| Please enter a valid value between the interval.");
    }

    /**
     * Validates if a given value falls within the acceptable interval defined by minimum and maximum values.
     *
     * @param value               The value to be validated.
     * @param minAcceptableValue  The minimum acceptable value of the interval.
     * @param maxAcceptableValue  The maximum acceptable value of the interval.
     * @throws IntervalException  If the value is not within the acceptable interval.
     */
    public static void validateValueBetweenInterval(double value, double minAcceptableValue, double maxAcceptableValue) throws IntervalException {

        boolean validNumber = value >= minAcceptableValue && value <= maxAcceptableValue;

        if (!validNumber){
            throw new IntervalException();
        }
    }
}
