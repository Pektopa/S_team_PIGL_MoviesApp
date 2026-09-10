package moviesapp.model.exceptions;

import static moviesapp.model.exceptions.IntervalException.validateValueBetweenInterval;

public class IndexException extends Exception {

    public IndexException() {
        super("\n| Please enter a valid index to select your element.");
    }

    /**
     * Checks if the given index is valid for a list of the specified size.
     *
     * @param index the index to check.
     * @param size the size of the list.
     * @throws IndexException if the index is negative or exceeds the size of the list.
     */
    public static void isValidIndex(int index, int size) throws IndexException {

        try {
            validateValueBetweenInterval(index, 0, size - 1);
        }
        catch (IntervalException e) {
            throw new IndexException();
        }
    }

    public String specifySearchModeError() {
        return "\n| Search Mode selection is not working here (wrong index or problem with request build).";
    }
}
