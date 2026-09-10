package moviesapp.model;

import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;
import moviesapp.model.exceptions.NoMovieFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MoviesTest {
    private final Movie movie1 = new Movie(true,null,null,"1",null,
            "movie1", null,0,null,"2023",null,true,
            0, 0);
    private final Movie movie2 = new Movie(true,null,null,"2",null,
            "movie2", null,0,null,"2023",null,true,
            0, 0);
    private final Movie movie3 = new Movie(true,null,null,"3",null,
            "movie3", null,0,null,"2023",null,true,
            0, 0);
    private final Movie movie4 = new Movie(true,null,null,"4",null,
            "movie4", null,0,null,"2023",null,true,
            0, 0);
    List<Movie> moviesToAddToMovies = new ArrayList<>();
    Movies emptyMovies = new Movies();
    @BeforeEach
    void setUpBeforeEach(){
        moviesToAddToMovies.add(movie1);
        moviesToAddToMovies.add(movie2);
        moviesToAddToMovies.add(movie3);
    }

    @Test
    void testToString(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertEquals(movies.toString(),
                indexOfMovie(1) + movie1 +
                        indexOfMovie(2) + movie2 +
                        indexOfMovie(3) + movie3);
        assertThat(emptyMovies.toString().equals("Your list of movies is empty.")).isTrue();
    }

    @Test
    void testAdd(){
        Movies movies = new Movies(moviesToAddToMovies);
        movies.add(movie4);
        assertEquals(movies.toString(),
                indexOfMovie(1) + movie1 +
                        indexOfMovie(2) + movie2 +
                        indexOfMovie(3) + movie3 +
                        indexOfMovie(4) + movie4);
    }

    @Test
    void testIsEmpty(){
        assertThat(emptyMovies.isEmpty()).isTrue();
        moviesToAddToMovies.add(movie3);
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.isEmpty()).isFalse();
    }

    @Test
    void testNoMovieFound(){
        Movies movies = new Movies(moviesToAddToMovies);

        assertThrows(NoMovieFoundException.class, () -> {
            Movies.searchableMovie(null);
        });

        assertDoesNotThrow(()-> {
            Movies.searchableMovie(movies);
        });

        assertThrows(NoMovieFoundException.class, () -> {
            Movies.searchableMovie(emptyMovies);
        });

    }

    @Test
    void testSize(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.size()).isEqualTo(3);
        movies.add(movie4);
        assertThat(movies.size()).isEqualTo(4);
        assertThat(emptyMovies.size()).isEqualTo(0);
    }

    @Test
    void testHasNext(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.iterator().hasNext()).isTrue();
        assertThat(emptyMovies.iterator().hasNext()).isFalse();
    }

    @Test
    void testNext(){
        Movies movies = new Movies();
        movies.add(movie1);
        assertThat(movies.iterator().next()).isEqualTo(movie1);
        assertThatThrownBy(() -> emptyMovies.iterator().next()).isInstanceOf
                (IllegalStateException.class).hasMessage("No more elements in the iterator.");
    }

    @Test
    void testFindMovie(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.findMovieByIndex(0)).isEqualTo(movie1);
        assertThat(movies.findMovieByIndex(1)).isEqualTo(movie2);
        assertThat(movies.findMovieByIndex(2)).isEqualTo(movie3);
        assertThat(movies.findMovieByIndex(3)).isEqualTo(null);
    }

    @Test
    void testRemove(){
        Movies movies = new Movies(moviesToAddToMovies);
        movies.remove(movie1);
        assertEquals(movies.toString(),
                indexOfMovie(1) + movie2 + indexOfMovie(2) + movie3);
        movies.remove((Movie) null);
        assertEquals(movies.toString(), indexOfMovie(1) + movie2 + indexOfMovie(2) + movie3);
        movies.remove(movie1);
        assertEquals(movies.toString(), indexOfMovie(1) + movie2 + indexOfMovie(2) + movie3);
    }

    @Test
    void testGet(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.get(0)).isEqualTo(movie1);
        assertThat(movies.get(1)).isEqualTo(movie2);
        assertThat(movies.get(2)).isEqualTo(movie3);
    }

    private String indexOfMovie(int index){
        return "| n°0" + index;
    }

    @Test
    void testToJsonFormat() {
        final Movies movies = getMovies();

        String expectedJson = """
                \s "results" : [
                    {
                      "adult" : false,
                      "backdrop_path" : "/635qI5pWhQSUaTnOkMo4GLCe8sV.jpg",
                      "genre_ids" : [28, 16, 12, 35, 14],
                      "id" : 19576,
                      "original_language" : "ja",
                      "original_title" : "ワンピース",
                      "overview" : "Overview of the movie",
                      "popularity" : 34.461,
                      "poster_path" : "/aRqQNSuXpcE3dkJC43aEg9f2HXd.jpg",
                      "release_date" : "2000-03-04",
                      "title" : "One Piece: The Movie",
                      "video" : false,
                      "vote_average" : 7.061,
                      "vote_count" : 309
                    },
                    {
                      "adult" : false,
                      "backdrop_path" : "/xTRnDuNqZKVAvXWa5tNq7k6Xuyb.jpg",
                      "genre_ids" : [16, 28, 12, 10770],
                      "id" : 1051942,
                      "original_language" : "ja",
                      "original_title" : "ルフィ落下! 秘境・海のヘソの大冒険",
                      "overview" : "",
                      "popularity" : 3.95,
                      "poster_path" : "/obptcr88HJ14tD2FFgg1zcWCmjs.jpg",
                      "release_date" : "2000-12-20",
                      "title" : "Luffy's Fall! The Unexplored Region - Grand Adventure in the Ocean's Navel",
                      "video" : false,
                      "vote_average" : 6.4,
                      "vote_count" : 7
                    }
                  ]""";

        assertEquals(expectedJson, movies.toJsonFormat());
    }

    @NotNull
    private static Movies getMovies() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(
                false,
                "/635qI5pWhQSUaTnOkMo4GLCe8sV.jpg",
                Arrays.asList("28", "16", "12", "35", "14"),
                "19576",
                "ja",
                "ワンピース",
                "Overview of the movie",
                34.461,
                "/aRqQNSuXpcE3dkJC43aEg9f2HXd.jpg",
                "2000-03-04",
                "One Piece: The Movie",
                false,
                7.061,
                309
        ));
        movieList.add(new Movie(
                false,
                "/xTRnDuNqZKVAvXWa5tNq7k6Xuyb.jpg",
                Arrays.asList("16", "28", "12", "10770"),
                "1051942",
                "ja",
                "ルフィ落下! 秘境・海のヘソの大冒険",
                "",
                3.95,
                "/obptcr88HJ14tD2FFgg1zcWCmjs.jpg",
                "2000-12-20",
                "Luffy's Fall! The Unexplored Region - Grand Adventure in the Ocean's Navel",
                false,
                6.4,
                7
        ));

        return new Movies(movieList);
    }
}
