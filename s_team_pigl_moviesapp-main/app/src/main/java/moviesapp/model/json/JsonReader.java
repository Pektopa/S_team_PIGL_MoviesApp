package moviesapp.model.json;
import com.fasterxml.jackson.databind.*;
import moviesapp.model.api.Genres;
import moviesapp.model.movies.Movie;
import moviesapp.model.movies.MovieFinder;
import moviesapp.model.movies.Movies;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class JsonReader extends MovieFinder {
    private final File jsonFile;
    private final ObjectMapper objectMapper;
    private final JsonNode jsonMovies ;
    private final JsonNode jsonGenres;
    public static final String SEARCH_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/json/search.json";
    public final static String FAVORITES_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/json/favorites.json";
    public final static String GENRES_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/json/genres.json";
    public static JsonReader SEARCH_READER = new JsonReader(SEARCH_FILE_PATH);
    public static JsonReader GENRES_READER = new JsonReader(GENRES_FILE_PATH);
    public static JsonReader FAVORITES_READER = new JsonReader(FAVORITES_FILE_PATH);

    public JsonReader(String path){
        jsonFile = new File(path);
        objectMapper = new ObjectMapper();
        jsonMovies = getJsonMoviesNode() ;
        jsonGenres = getJsonGenresNode();
    }

    /**
     * Updates the search reader with the specified API file path.
     *
     * @return a JsonReader object initialized with the API file path
     */
    public static JsonReader updateSearchReader() {
        return new JsonReader(SEARCH_FILE_PATH);
    }

    public static JsonReader updateGenresReader() {
        return new JsonReader(GENRES_FILE_PATH);
    }

    public static JsonReader updateFavoritesReader() {
        return new JsonReader(FAVORITES_FILE_PATH);
    }


    /**
     * Convert a jsonNode to a Movie
     * @param jsonNode: the jsonNode to convert to a movie
     * @return the jsonNode converted to a movie
     */
    private Movie jsonNodeToMovie(JsonNode jsonNode) {
        return new Movie(
                jsonNode.get("adult").asBoolean(),
                escapeQuotes(jsonNode.get("backdrop_path").asText()),
                getGenresFromJson(jsonNode),
                escapeQuotes(jsonNode.get("id").asText()),
                escapeQuotes(jsonNode.get("original_language").asText()),
                escapeQuotes(jsonNode.get("original_title").asText()),
                escapeQuotes(jsonNode.get("overview").asText()),
                jsonNode.get("popularity").asDouble(),
                escapeQuotes(jsonNode.get("poster_path").asText()),
                escapeQuotes(jsonNode.get("release_date").asText()),
                escapeQuotes(jsonNode.get("title").asText()),
                jsonNode.get("video").asBoolean(),
                jsonNode.get("vote_average").asDouble(),
                jsonNode.get("vote_count").asInt()
        );
    }

    /**
     * Escapes double quotes in a string by adding backslashes (\).
     * @param input the string in which to escape double quotes
     * @return the string with escaped double quotes
     */
    private String escapeQuotes(String input) {
        return input.replaceAll("\"", "\\\\\"");
    }

    /**
     * Browse genre_ids jsonNode to collect values to store in a list
     * @param jsonNode to browse
     * @return the list of genre identifiers
     */
    private List<String> getGenresFromJson(JsonNode jsonNode){
        JsonNode jsonGenreIds = jsonNode.get("genre_ids");
        List<String> genreIds = new ArrayList<>();

        for(JsonNode jsonGenreId: jsonGenreIds){
            genreIds.add(jsonGenreId.asText());
        }
        return genreIds;
    }

    @Override
    public void findMoviesByCriteria(Movies movies, String title, String releaseYear, List<String> genres, String minVoteAverage) {
        for (JsonNode movie : jsonMovies) {
            boolean titleCondition = title == null ||
                    title.isEmpty() ||
                    movie.get("original_title").asText().toLowerCase().contains(title.toLowerCase());
            boolean yearCondition = releaseYear == null ||
                    releaseYear.isEmpty() ||
                    movie.get("release_date").asText().startsWith(releaseYear);
            boolean genreCondition =
                    genres == null ||
                            genres.isEmpty() ||
                            movieContainsAnyGenre(movie, genres);
            boolean voteCondition =
                    minVoteAverage == null ||
                            minVoteAverage.isEmpty() ||
                            Double.parseDouble(minVoteAverage) <= movie.get("vote_average").asDouble();

            if (titleCondition && yearCondition && genreCondition && voteCondition) {
                movies.add(jsonNodeToMovie(movie));
            }
        }
    }

    /**
     * Checks if the given movie matches any of the provided genre IDs.
     * @param movie The JSON node representing the movie.
     * @param genres The list of genre to match against.
     * @return {@code true} if the movie matches any of the provided genre IDs, {@code false} otherwise.
     */
    private boolean movieContainsAnyGenre(JsonNode movie, List<String> genres) {
        List<String> genreIds = Genres.genresToGenreIds(genres);

        for (JsonNode genreIdNode : movie.get("genre_ids")) {
            String genreId = genreIdNode.asText();
            if (genreIds.contains(genreId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and returns all movies from the JSON movie data.
     *
     * @return a Movies object containing all the movies found in the JSON data,
     *         or an empty Movies object if no movies are found or if the JSON data is null
     */
    public Movies findAllMovies() {
        Movies movieList = new Movies();
        if(jsonMovies != null){
            for(JsonNode jsonMovie : jsonMovies ){
                movieList.add(jsonNodeToMovie(jsonMovie));
            }
            return movieList;
        }
        return movieList;
    }

    /**
     * Return the origin jsonNode from our default jsonFile with exception management
     * @return the origin jsonNode from our default jsonFile
     */
    private JsonNode getJsonMoviesNode(){
        return getSpecificJsonNode("results");
    }

    /**
     * Return the number of total pages of movies available in the json file of the class (give the number 500 if it
     * is more than 500 pages)
     * @return the number of total pages of movies available in the json file of the class
     */
    public int numberOfPagesOfMoviesInJson(){
        JsonNode totalPagesNode = getSpecificJsonNode("total_pages");
        int numberOfPages = 0;

        if(totalPagesNode != null){
            numberOfPages = totalPagesNode.asInt();

            if(numberOfPages > 500){
                numberOfPages = 500;
            }
        }

        return numberOfPages;
    }

    /**
     * return the actual page générate in api-resut
     * @return the actual page
     */
    public int getPageInJson(){
        JsonNode pageNode = getSpecificJsonNode("page");

        if(pageNode!= null){
            return pageNode.asInt();
        }

        return -1;
    }

    /**
     * Return a specific jsonNode from our json file of the class
     * @return a specific jsonNode from our json file of the class
     */
    private JsonNode getSpecificJsonNode(String jsonNodeName){
        try{
            return objectMapper.readTree(jsonFile).get(jsonNodeName);
        }
        catch (IOException e) {
            System.err.println("IOException: objectMapper.readTree(jsonFile) exception" + e.getMessage());
        }
        catch (NullPointerException e){
            System.err.println("NullPointerException: objectMapper.readTree(jsonFile).get(\"results\") exception" + e.getMessage());
        }
        return null;
    }

    /**
     * Return the origin jsonNode from our default jsonFile with exception management
     * @return the origin jsonNode from our default jsonFile
     */
    private JsonNode getJsonGenresNode() {
        if(!isFileEmpty(jsonFile)){
            try {
                return objectMapper.readTree(jsonFile).get("genres");
            } catch (IOException e) {
                System.err.println("IOException: objectMapper.readTree(jsonFile) exception");
            } catch (NullPointerException e) {
                System.err.println("NullPointerException: objectMapper.readTree(jsonFile).get(\"genres\") exception");
            }
        }
        return null;
    }

    public JsonNode getJsonGenres(){
        return jsonGenres;
    }

    /**
     * Checks if the specified file is empty.
     * @param file The file to check.
     * @return {@code true} if the file is empty or does not exist, {@code false} otherwise.
     */
    public static boolean isFileEmpty(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            System.err.println("IOException from public static boolean isFileEmpty(String filePath) in JsonReader.java");
            return true;
        }
    }

}