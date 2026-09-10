package moviesapp.model;
import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;
import moviesapp.model.json.JsonReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static moviesapp.model.json.JsonReader.updateSearchReader;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class JsonReaderTest {

    private static JsonReader jsonReader;
    private static List<String> emptyGenres;
    private static List<String> actionAdventureGenres;

    @BeforeAll
    static void setupBeforeAll(){
        jsonReader = new JsonReader(System.getProperty("user.dir") + "/src/test/resources/data-example.json");
        emptyGenres = new ArrayList<>();
        actionAdventureGenres = new ArrayList<>();
        actionAdventureGenres.add("action");
        actionAdventureGenres.add("adventure");
    }

    @Test
    public void testUpdateSearchReader() {
        JsonReader reader = updateSearchReader();
        assertNotNull(reader);
    }

    @Test
    void testFindMovies(){

        Movies movieList1 = jsonReader.findMovies("Monkeyshines" , "", emptyGenres, "") ;
        Movies movieList2= jsonReader.findMovies("", "1890", emptyGenres, "") ;
        Movies movieList3 = jsonReader.findMovies("" , "", actionAdventureGenres, "") ;
        Movies movieList4 = jsonReader.findMovies("" , "", emptyGenres, "4") ;
        Movies movieList5 = jsonReader.findMovies("" , "", emptyGenres, "") ;

        for (Movie movie : movieList1){
            assertThat(movie.title().contains("Monkeyshines")).isTrue() ;
        }
        for (Movie movie : movieList2){
            assertThat(movie.releaseDate().contains("1890")).isTrue();
        }
        for (Movie movie : movieList3){
            assertThat(movie.genres().contains("action") && movie.genres().contains("adventure")).isTrue();
        }
        for (Movie movie : movieList4){
            assertThat(movie.minVoteAverage() >= 4).isTrue() ;
        }
        assertThat(movieList5 == null).isTrue();

        Movies movieListNull0= jsonReader.findMovies(null , null, null, null) ;
        Movies movieListNull1 = jsonReader.findMovies("Monkeyshines" , null, null, null) ;
        Movies movieListNull2= jsonReader.findMovies(null , "1890", null, null) ;
        Movies movieListNull3 = jsonReader.findMovies(null , null, new ArrayList<>(), null) ;
        Movies movieListNull4 = jsonReader.findMovies(null , null, null, "");
        assertThat(movieListNull0 == null).isTrue() ;
        assertThat(movieListNull1 == null).isTrue() ;
        assertThat(movieListNull2 == null).isTrue() ;
        assertThat(movieListNull3 == null).isTrue() ;
        assertThat(movieListNull4 == null).isTrue() ;
    }

    @Test
    void testFindAllMovies(){
        Movies movieList = jsonReader.findAllMovies();
        assertThat(movieList.get(0).title().equals("Monkeyshines, No. 1")).isTrue();
        assertThat(movieList.get(0).voteCount() == 90).isTrue();
        assertThat(movieList.get(1).title().equals("Monkeyshines, No. 2")).isTrue();
        assertThat(movieList.get(1).voteCount() == 47).isTrue();
        assertThat(movieList.get(9).title().equals("Escrime")).isTrue();
        assertThat(movieList.get(9).voteCount() == 4).isTrue();
        assertThat(movieList.size() == 10).isTrue();
    }
}
