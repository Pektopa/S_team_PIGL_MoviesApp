package moviesapp.controller.command_line;

import moviesapp.model.exceptions.IndexException;
import moviesapp.model.exceptions.NoMovieFoundException;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movies;

import static moviesapp.model.json.JsonWriter.FAVORITES_WRITER;
import static moviesapp.model.movies.Favorites.addByIndex;
import static moviesapp.model.movies.Favorites.removeByIndex;
import static moviesapp.model.movies.Movies.moviesFromPreviousSearch;

public class CLFavorites {

    private final CLController controller;

    public CLFavorites(CLController controller) {
        this.controller = controller;
    }

    /**
     * Displays the user's favorite movie list and returns it as Movies object.
     */
    Movies display() {
        System.out.print("Your favorite list:\n" + Favorites.instance);
        return Favorites.asMovies();
    }

    /**
     * Executes the add command, allowing the user to add movies to their favorites.
     */
    void add() {
        System.out.println("Add command has been started.");
        Movies movies = moviesFromPreviousSearch();
        System.out.println();

        try {
            Movies.searchableMovie(movies);
        }
        catch (NoMovieFoundException e) {
            System.out.println("\n| Make sure to use search command before add command, otherwise your search result was null.");
            return;
        }

        boolean addSuccess = false;

        while (!addSuccess) {
            try {
                addCommandTry(movies);
                addSuccess = true;
            } catch (IndexException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Remove to the favorites the movie chosen by the user
     */
    void remove() {
        System.out.println("Remove command has been started.\n");
        Movies movies = display();
        System.out.println();

        try {
            Movies.searchableMovie(movies);
        }
        catch (NoMovieFoundException e) {
            System.out.println("\n| Be sure to have movies in your favorites before trying to use remove command.");
            return;
        }

        boolean removeSuccess = false;

        while (!removeSuccess) {
            try {
                removeCommandTry(movies);
                removeSuccess = true;
            } catch (IndexException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Attempts to add movies to favorites by index from the given list of movies.
     *
     * @param movies the list of movies from which to select movies to add.
     * @throws IndexException if an invalid index is provided during the addition process.
     */
    private void addCommandTry(Movies movies) throws IndexException { //TODO: refactor this method and removeCommandTry

        do{
            addByIndex(movies, controller.retrieveAsPositiveInt("Index of the movie to add to your favorites: ") - 1);
        }
        while(controller.askToConfirm("Do you want to add another movie?"));

        System.out.println();
        display();
    }

    /**
     * Attempts to remove movies to favorites by index from the given list of movies.
     *
     * @param movies the list of movies from which to select movies to remove.
     * @throws IndexException if an invalid index is provided during the removing process.
     */
    private void removeCommandTry(Movies movies) throws IndexException {

        do{
            removeByIndex(movies, controller.retrieveAsPositiveInt("Index of the movie to remove from your favorites: ") - 1);
        }
        while(controller.askToConfirm("Do you want to remove another movie?"));

        System.out.println();
        display();
    }

    /**
     * Clears the favorites list after confirming with the user.
     */
    void clear() {
        if (controller.askToConfirm("Are you sure that you want to delete your favorites?")){
            Favorites.clear();
            FAVORITES_WRITER.clear();
            System.out.println("Your favorite list has been cleared.");
        }
    }
}
