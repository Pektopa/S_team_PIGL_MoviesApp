package moviesapp.model.movies;

import moviesapp.model.exceptions.IndexException;
import moviesapp.model.exceptions.NoMovieFoundException;
import moviesapp.model.exceptions.NotAPositiveIntegerException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static moviesapp.model.api.RequestBuilder.convertAsPositiveInt;
import static moviesapp.model.json.JsonReader.SEARCH_READER;
import static moviesapp.model.exceptions.IndexException.isValidIndex;

public class Movies implements Iterable<Movie> {
    private final List<Movie> movies;

    public Movies(List<Movie> movies){
        this.movies = movies;
    }

    public Movies(){
        this.movies = new ArrayList<>();
    }

    @Override
    public String toString(){
        StringBuilder moviesString = new StringBuilder();
        if(movies == null || movies.isEmpty()){
            return "Your list of movies is empty.";
        }
        for(int i = 0; i < movies.size(); i++){
            moviesString.append("| n°");
            if (i < 10){
                moviesString.append(0).append(i + 1);
            }
            else{
                moviesString.append(i + 1);
            }
            moviesString.append(movies.get(i));
        }
        return moviesString.toString();
    }

    /**
     * Add a movie to the list
     * @param movie the movie that we want to add
     */
    public void add(Movie movie){
        if(movie != null) {
            movies.add(movie);
        }
    }

    /**
     * Return true if the list of movies is empty, otherwise false
     * @return true if the list of movies is empty, otherwise false
     */
    public boolean isEmpty(){
        return movies.isEmpty();
    }

    /**
     * Checks if the given list of movies is empty or null.
     *
     * @param movies the list of movies to check.
     * @throws NoMovieFoundException if the list of movies is empty or null.
     */
    public static void searchableMovie(Movies movies) throws NoMovieFoundException{

        boolean noMovieFound = movies == null || movies.isEmpty();

        if(noMovieFound){
            throw new NoMovieFoundException();
        }
    }

    /**
     * Return the size of the movies list
     * @return the size of the movies list
     */
    public int size(){
        return movies.size();
    }

    @NotNull
    @Override
    public Iterator<Movie> iterator() {
        return new MoviesIterator();
    }

    private class MoviesIterator implements Iterator<Movie> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Movie next() {
            if (hasNext()) {
                return movies.get(index++);
            } else {
                throw new IllegalStateException("No more elements in the iterator.");
            }
        }
    }

    /**
     * Return of a movie selected with a list of movies and an id provided in parameter.
     * @param index of movie to find
     * @return the movie found with selection or null
     */
    public Movie findMovieByIndex(int index) {
        if (movies == null || index < 0 || index >= size()) {
            return null;
        }
        return get(index);
    }

    /**
     * Remove a movie from the movies list
     * @param movie the movie that we want to remove in our list
     */
    public void remove(Movie movie){
        movies.remove(movie);
    }

    /**
     * Return a movie according to an index provided in parameter
     * @param index to find our movie in movies list
     * @return the Movie found with the index
     */
    public Movie get(int index){
        return movies.get(index);
    }

    /**
     * Converts the list of movies to JSON format by returning the string corresponding.
     * @return a JSON formatted string representing the list of movies
     */
    public String toJsonFormat() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("  \"results\" : [\n");

        for (Movie movie : movies) {
            jsonBuilder.append(movie.toJsonFormat());
            if (movies.indexOf(movie) < movies.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("  ]");
        return jsonBuilder.toString();
    }

    /**
     * Remove all the movies registered in movies list.
     */
    public void clear(){
        movies.clear();
    }

    /**
     * Selects a movie from the given list of movies by index.
     *
     * @param movies the list of movies from which to select.
     * @param index the index of the movie to select.
     * @return the selected movie.
     * @throws IndexException if the index provided is not valid
     */
    public static Movie selectMovieByIndex(Movies movies, int index) throws IndexException {

        isValidIndex(index, movies.size());
        return movies.findMovieByIndex(index);
    }

    /**
     * Tries to select a movie by its index from the provided movies' collection.
     * Continuously attempts movie selection until successful.
     *
     * @param movies The collection of movies to select from.
     * @return The selected movie.
     */
    public static Movie selectMovieByIndexTry(Movies movies, String index) throws NotAPositiveIntegerException, IndexException {

        int positiveIndex = convertAsPositiveInt(index) - 1;
        isValidIndex(positiveIndex, movies.size());

        return selectMovieByIndex(movies, positiveIndex);
    }

    /**
     * Retrieves the list of movies from the previous search and displays them.
     *
     * @return the list of movies from the previous search.
     */
    public static Movies moviesFromPreviousSearch(){
        System.out.print("\nBelow the movies from your precedent search:\n" + SEARCH_READER.findAllMovies());
        return SEARCH_READER.findAllMovies();
    }

    /**
     * Retrieves the list of movies encapsulated within this object.
     *
     * @return The list of movies.
     */
    public List<Movie> getMovieList() {
        return movies;
    }
}
