package moviesapp.controller.command_line;

import moviesapp.controller.command_line.exceptions.ExitException;
import moviesapp.model.exceptions.*;

import java.util.*;

import static moviesapp.model.api.RequestBuilder.convertAsPositiveInt;

public class CLController {

    private CLGeneral generalCommands;
    private CLSearch searchCommands;
    protected CLFavorites favoritesCommands;
    private final Scanner scanner;

    public CLController() {
        scanner = new Scanner(System.in);
        initCommands();
    }

    /**
     * Initializes the command line commands.
     */
    private void initCommands() {
        generalCommands = new CLGeneral(this);
        searchCommands = new CLSearch(this);
        favoritesCommands = new CLFavorites(this);
    }

    /**
     * Select and execute a command based on user input
     */
    public void select() {
        boolean exitRequested = false;

        do {
            generalCommands.help();

            String commandValue = askValue("\nInput your command: ").toLowerCase(Locale.ROOT).trim();
            System.out.println();

            switch (commandValue) {
                case "1":
                    searchCommands.catalog();
                    break;

                case "2":
                    searchCommands.search();
                    break;

                case "3":
                    generalCommands.details();
                    break;

                case "4":
                    favoritesCommands.add();
                    break;

                case "5":
                    favoritesCommands.remove();
                    break;

                case "6":
                    favoritesCommands.display();
                    break;

                case "7":
                    favoritesCommands.clear();
                    break;

                case "8":
                    try {
                        generalCommands.exit();
                    } catch (ExitException e) {
                        exitRequested = true;
                    }
                    break;

                default:
                    System.out.println("*** Command '" + commandValue + "' doesn't exist. ***");
                    break;
            }

        } while (!exitRequested);

        System.exit(0);
    }

    /**
     * Asks the user to confirm an action.
     *
     * @param string the prompt to display asking for confirmation.
     * @return {@code true} if the user confirms (enters 'Y' or 'y'), {@code false} otherwise.
     */
    protected boolean askToConfirm(String string) {
        String answer;
        do{
            answer = askValue(string + " [Y/n]: ").trim().toLowerCase();
        }while(!answer.equals("y") && !answer.equals("n"));

        return answer.equals("y");
    }

    /**
     * Asks the user for input with the given message and returns the input as a String.
     *
     * @param message the message to display prompting the user for input.
     * @return the user's input as a String.
     */
    protected String askValue(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Prompts the user with a message to enter a positive integer value.
     * Continuously prompts until a valid positive integer value is entered.
     *
     * @param message the message to display when prompting the user for input
     * @return the positive integer value entered by the user
     */
    protected int retrieveAsPositiveInt(String message) {

        int valueConverted ;
        try{
            valueConverted = convertAsPositiveInt(askValue(message));
        }
        catch (NotAPositiveIntegerException e) {
            System.out.println(e.getMessage());
            return retrieveAsPositiveInt(message);
        }
        return valueConverted;
    }

    /**
     * Tries to select a mode based on the provided message and list of option numbers.
     * Continuously attempts mode selection until successful.
     *
     * @param message        The message related to selecting a mode.
     * @param optionNumbers  The list of option numbers representing available modes.
     * @return               The selected mode.
     */
    protected String selectModeTry(String message, List<String> optionNumbers) {

        String modeSelected;
        try{
            modeSelected = selectMode(message, optionNumbers);
        }
        catch(IndexException e){
            System.out.println(e.getMessage());
            return selectModeTry(message, optionNumbers);
        }
        return modeSelected;
    }

    /**
     * Selects a mode based on provided options.
     *
     * @param message The message prompting the user for input.
     * @param optionNumbers The list of valid option numbers.
     * @return The selected mode.
     */
    private String selectMode(String message, List<String> optionNumbers) throws IndexException {
        String selectMode = askValue(message);

        if(!optionNumbers.contains(selectMode)){
            throw new IndexException();
        }
        return selectMode;
    }

}