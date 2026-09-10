package moviesapp.model;

import moviesapp.model.api.SearchCriteria;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.exceptions.SelectModeException;
import moviesapp.model.json.JsonReader;
import moviesapp.model.movies.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static moviesapp.model.api.Genres.GENRE_NAME_ID_MAP;
import static moviesapp.model.api.Genres.fillGENRE_NAME_ID_MAP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheMovieDbAPITest {

    @Test
    void testSearchMoviesWithCriteria() throws SelectModeException {

        assertThrows(SelectModeException.class, () -> TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","",new ArrayList<>(),"","")));

        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","",new ArrayList<>(),"6",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat(movie.minVoteAverage()).isGreaterThanOrEqualTo(6);
        }

        List<String> genres = new ArrayList<>();
        genres.add("Action");
        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","",genres,"",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat(movie.genres().contains("Action")).isTrue();
        }

        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","",genres,"6",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat(movie.minVoteAverage()).isGreaterThanOrEqualTo(6);
            assertThat(movie.genres().contains("Action")).isTrue();
        }

        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","1900",new ArrayList<>(),"",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat( movie.getReleaseYear().substring(0,4).compareTo("1900")).isLessThanOrEqualTo(0);
        }

        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","1900",new ArrayList<>(),"6",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat(movie.minVoteAverage()).isGreaterThanOrEqualTo(6);
            assertThat( movie.getReleaseYear().substring(0,4).compareTo("1900")).isLessThanOrEqualTo(0);
        }

        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","1900","2000", genres,"6",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat( movie.minVoteAverage()).isGreaterThanOrEqualTo(6);
            assertThat( movie.getReleaseYear().substring(0,4).compareTo("2000")).isLessThanOrEqualTo(0);
            assertThat( movie.getReleaseYear().substring(0,4).compareTo("1900")).isGreaterThanOrEqualTo(0);
            assertThat(movie.genres().contains("Action")).isTrue();
        }

        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("One Piece","","", new ArrayList<>(),"",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat(movie.title().contains("One Piece")).isTrue();
        }

        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("One Piece","1900","1900", new ArrayList<>(),"",""));
        for(Movie movie : new JsonReader(JsonReader.SEARCH_FILE_PATH).findAllMovies()){
            assertThat(movie.title().contains("One Piece")).isTrue();
            assertThat(movie.getReleaseYear().substring(0,4).compareTo("1900")).isEqualTo(0);
        }

    }

    @Test
    void testFillGENRE_ID_MAP(){
        assertThat(GENRE_NAME_ID_MAP.isEmpty()).isTrue();
        fillGENRE_NAME_ID_MAP();
        assertThat(GENRE_NAME_ID_MAP.isEmpty()).isFalse();
        assertEquals(GENRE_NAME_ID_MAP.toString(),(  "{Action=28, Adventure=12, Animation=16, Comedy=35, Crime=80, Documentary=99, " +
                "Drama=18, Family=10751, Fantasy=14, History=36, Horror=27, Music=10402, Mystery=9648, Romance=10749, Science Fiction=878, " +
                "TV Movie=10770, Thriller=53, War=10752, Western=37}"));

    }
}
