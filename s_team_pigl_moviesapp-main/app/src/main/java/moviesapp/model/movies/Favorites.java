package moviesapp.model.movies;
import moviesapp.model.exceptions.IndexException;

import java.util.ArrayList;
import java.util.List;

import static moviesapp.model.json.JsonReader.FAVORITES_READER;
import static moviesapp.model.json.JsonWriter.FAVORITES_WRITER;
import static moviesapp.model.movies.Movies.selectMovieByIndex;

public class Favorites extends MovieFinder {
    public static final Favorites instance = new Favorites();
    private static final Movies favorites = FAVORITES_READER.findAllMovies();


    /** Return true if the list of favorites is empty, if not return false
     *  @return boolean
     **/
    public static boolean isEmpty(){
        return favorites.isEmpty();
    }

    /**
     * Return the Movies composing user favorites
     * @return the Movies composing user favorites
     */
    public static Movies asMovies(){
        return favorites;
    }

    @Override
    public String toString(){
        return favorites.toString();
    }

    /**
     * Remove all the movies registered in user favorite list.
     */
    public static void clear(){
        try{
            favorites.clear();
        }
        catch(UnsupportedOperationException e){
            System.out.println("The operation was unsuccessful.");
        }
    }

    /**
     * Checks if a movie is already in the favorites list.
     * @param movie The movie to check.
     * @return True if the movie is already in favorites, false otherwise.
     */
    public static boolean contains(Movie movie){
        return favoritesIds().contains(movie.id());
    }

    /**
     * Retrieves the IDs of favorite movies.
     * @return A list of IDs representing favorite movies.
     */
    private static List<String> favoritesIds(){
        List<String> favoritesIds = new ArrayList<>();

        for (Movie favorite : favorites){
            favoritesIds.add(favorite.id());
        }
        return favoritesIds;
    }

    public void findMoviesByCriteria(Movies movies, String title, String releaseYear, List<String> genres, String minVoteAverage) {
        for (Movie movie : favorites) {
            boolean titleCondition = (title == null) ||
                    movie.originalTitle().toLowerCase().contains(title.toLowerCase());
            boolean yearCondition = (releaseYear == null) ||
                    movie.releaseDate().toLowerCase().startsWith(releaseYear.toLowerCase());
            boolean genreCondition = (genres == null || genres.isEmpty()) ||
                    movieContainsAnyGenre(movie, genres);
            boolean voteCondition = (minVoteAverage == null) ||
                    Double.parseDouble(minVoteAverage) <= movie.minVoteAverage();

            if (titleCondition && yearCondition && genreCondition && voteCondition) {
                movies.add(movie);
            }
        }
    }

    /**
     * Checks if the movie contains at least one genre from the specified list.
     * @param movie The movie to check.
     * @param genres The list of genres to search for in the movie.
     * @return {@code true} if the movie contains at least one of the specified genres, {@code false} otherwise.
     */
    private static boolean movieContainsAnyGenre(Movie movie, List<String> genres) {
        for (String genre : genres) {
            if (movie.genres().contains(genre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a movie from the given list of movies to the favorites list at the specified index.
     *
     * @param movies the list of movies from which to select the movie to add.
     * @param index the index of the movie to add to the favorites list.
     * @throws IndexException if the index provided is not valid
     */
    public static void addByIndex(Movies movies, int index) throws IndexException { //TODO: refactor this method and removeByIndex

        if (movies.size() > 1){
            add(selectMovieByIndex(movies, index));
        }
        else{
            add(movies.get(0));
        }
        saveFavorites();

    }

    /**
     * Removes a movie from the given list of movies to the favorites list at the specified index.
     *
     * @param movies the list of movies from which to select the movie to remove.
     * @param index the index of the movie to remove from the favorites list.
     * @throws IndexException if the index provided is not valid
     */
    public static void removeByIndex(Movies movies, int index) throws IndexException {

        if (movies.size() > 1){
            remove(selectMovieByIndex(movies, index));
        }
        else{
            remove(movies.get(0));
        }
        saveFavorites();

    }

    /**
     * Adds a movie to the favorites list if it is not null and not already contained in the favorites.
     *
     * @param movie the movie to add to the favorites list.
     */
    public static void add(Movie movie){ //TODO: refactor this method and remove

        if(movie != null && !contains(movie)){
            favorites.add(movie);
        }
    }

    /**
     * Removes a movie to the favorites list if it's already present and if it's not null.
     *
     * @param movie The movie to remove to favorites.
     */
    public static void remove(Movie movie){

        if(movie != null && contains(movie)){
            favorites.remove(movie);
        }
    }

    /**
     * Saves the current favorites to the storage.
     */
    private static void saveFavorites(){
        FAVORITES_WRITER.saveFavorites(asMovies());
    }
}
