package moviesapp.controller.command_line;

import moviesapp.model.api.Genres;
import moviesapp.model.api.SearchCriteria;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.exceptions.*;
import moviesapp.model.movies.Movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static moviesapp.model.api.Genres.GENRE_NAME_ID_MAP;
import static moviesapp.model.api.Genres.genresToGenreIds;
import static moviesapp.model.api.RequestBuilder.*;
import static moviesapp.model.api.RequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.TheMovieDbAPI.checkMinVoteAverage;
import static moviesapp.model.api.TheMovieDbAPI.checkYears;
import static moviesapp.model.exceptions.IndexException.isValidIndex;
import static moviesapp.model.json.JsonReader.SEARCH_READER;

public class CLSearch {

    private SearchCriteria criteria;
    private final CLController controller;

    public CLSearch(CLController controller) {
        this.controller = controller;
    }

    /**
     * Displays the catalog of popular movies.
     * It retrieves the popular movies from the API and displays the list of popular movies.
     * It prompts the user to navigate through pages of the catalog.
     */
    void catalog() {

        TheMovieDbAPI.popularMoviesFirstPage();
        do{
            System.out.println("The most popular movies at the moment are listed below: \n" + SEARCH_READER.findAllMovies());

        } while(searchPageManagement());
    }

    /**
     * Generates a message for page management actions.
     *
     * @return The generated page management message.
     */
    private String pageManagementMessage() {
        return "Choose your action: [0] Continue/Leave command, [1] Previous Page, [2] Next Page, [3] Specify Page | page ("
                + SEARCH_READER.getPageInJson()
                + "/"
                + SEARCH_READER.numberOfPagesOfMoviesInJson() +")";
    }

    /**
     * Manages the page navigation within the current search.
     *
     * @return {@code false} if the user chooses to continue or leave the command, otherwise {@code true}.
     */
    private boolean searchPageManagement() {
        String response = controller.selectModeTry(pageManagementMessage(), Arrays.asList("0","1","2","3"));

        try {
            switch (response) {
                case "0" -> {
                    return false;
                }
                case "1" -> TheMovieDbAPI.switchToPreviousPage();

                case "2" -> TheMovieDbAPI.switchToNextPage();

                case "3" -> searchSpecificPageTry();

                default -> System.out.println(new SelectModeException().getMessage());
            }
        }
        catch (NoPreviousPageException | NoNextPageException e) {
            System.out.println(e.getMessage());
            return searchPageManagement();
        }

        System.out.println();
        return true;
    }

    /**
     * Attempts to navigate to a specific page of current search using a loop until successful.
     */
    private void searchSpecificPageTry() {

        try {
            TheMovieDbAPI.switchToSpecificPage(controller.askValue("Enter a page number: "));
        }
        catch (IntervalException | NotAPositiveIntegerException e) {
            System.out.println(e.getMessage());
            searchSpecificPageTry();
        }
    }

    /**
     * Initiates a search for movies.
     * This method retrieves search criteria from the user. If the criteria are not fully provided, the search is aborted.
     * Otherwise, it launches the search using TheMovieDbAPI with the provided criteria and page number '1',
     * then prints the search results.
     */
    void search() {

        retrieveCriteriaFromUser();

        try {
            TheMovieDbAPI.searchMoviesWithCriteria(criteria);
        }
        catch (SelectModeException e) {
            System.out.println("\n| No information provided, launch catalog command by default.\n");
            catalog();
            return;
        }
        printSearchResults();
    }

    /**
     * Retrieves search criteria from the user.
     */
    private void retrieveCriteriaFromUser() {
        criteria = new SearchCriteria();

        criteria.title = controller.askValue("Title of the movie: ");

        String[] yearsFromUser = yearsFromUser(
                "Min release year [ ≧ " + minAcceptableYearValue + "]: ",
                "Max release year [ ≦ " + maxAcceptableYearValue + "]: ");

        criteria.minYear = yearsFromUser[0];
        criteria.maxYear = yearsFromUser[1];

        criteria.minVoteAverage = minVoteAverageFromUser();
        criteria.genreIds = genresToGenreIds(genresFromUser());
    }

    /**
     * Retrieves years input from the user.
     *
     * @param minYearMessage The message to ask for the minimum year.
     * @param maxYearMessage The message to ask for the maximum year.
     * @return An array containing the minimum and maximum years provided by the user.
     */
    private String[] yearsFromUser(String minYearMessage, String maxYearMessage) {

        String minYear;
        String maxYear;
        try {
            minYear = controller.askValue(minYearMessage);
            maxYear = controller.askValue(maxYearMessage);
            checkYears(minYear, maxYear);
        }
        catch (IntervalException | NotAPositiveIntegerException | NotValidYearsException e){
            System.out.println(e.getMessage());
            return yearsFromUser(minYearMessage, maxYearMessage);
        }
        return new String[]{minYear, maxYear};
    }

    /**
     * Retrieves the minimum vote average for a movie.
     *
     * @return The minimum vote average provided by the user.
     */
    private String minVoteAverageFromUser() {

        String minVoteAverage;
        try {
            minVoteAverage = controller.askValue("Movie's minimum rate [0-10]: ");
            checkMinVoteAverage(minVoteAverage);
        }
        catch (NotAPositiveIntegerException | IntervalException e) {
            System.out.println(e.getMessage());
            return minVoteAverageFromUser();
        }
        return minVoteAverage;
    }

    /**
     * Prompts the user to select one or more genres for the search.
     *
     * @return The list of genres selected by the user.
     */
    private List<String> genresFromUser() {

        List<String> genres = new ArrayList<>();

        if(controller.askToConfirm("Do you want to specify one or more genres?")){
            System.out.print("\nList of genres: \n" + Genres.getGenres());
            selectGenres(genres);
        }
        return genres;
    }

    /**
     * Allows the user to select genres in a loop until they choose to stop.
     *
     * @param genres The list of genres already selected.
     *               This list will be updated with newly selected genres.
     */
    private void selectGenres(List<String> genres) {
        do{
            List<String> genreNames = new ArrayList<>(GENRE_NAME_ID_MAP.keySet());

            String genreSelected = selectGenreByIndex(genreNames);

            if (!genres.contains(genreSelected)) {
                genres.add(genreSelected);
            }
            else {
                System.out.println("\n| Please enter a valid genre that is not already selected.");
            }
        } while(controller.askToConfirm("Do you want to add more genres? | Genres already selected: " + genres));
    }

    /**
     * Allows the user to select a genre from the provided list by index.
     *
     * @param genres The list of genres to choose from.
     * @return The selected genre.
     */
    private String selectGenreByIndex(List<String> genres) {

        try {
            int index = controller.retrieveAsPositiveInt("\nEnter genre index: ") - 1;
            isValidIndex(index, genres.size());
            return genres.get(index);
        }
        catch (IndexException e){
            System.out.println(e.getMessage());
            return selectGenreByIndex(genres);
        }
    }

    /**
     * Prints the search results.
     * It prompts the user to navigate through pages of the search results.
     */
    private void printSearchResults() {

        try {
            do{
                Movies moviesFromSearch = SEARCH_READER.findAllMovies();
                Movies.searchableMovie(moviesFromSearch);
                System.out.println("\nYour list of movies found in your search: \n" + moviesFromSearch);
            }
            while(searchPageManagement());
        }
        catch (NoMovieFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
