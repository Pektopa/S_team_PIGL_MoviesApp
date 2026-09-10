package moviesapp.model;

import moviesapp.model.movies.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MovieTest {

    static List<String> genres;

    @BeforeAll
    static void setup(){
        genres = new ArrayList<>();
        genres.add("action");
    }

    @Test
    void testDetails(){
        Movie movie1 = new Movie(true,"backdropPath",genres,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDate",
                "title",false,3,4);
        assertThat(movie1.details()).isEqualTo("__________________________  \nmovie n°1:\n  title: title\n  original title: originalTitle"+
                "\n  release date: releaseDate\n  original language: originalLanguage\n  genreIds: "+genres+
                "\n  popularity: 2.0\n  adult: true\n  vote average: 3.0\n  vote count: 4\n  overview: overview\n__________________________  ");
    }

    @Test
    void testToString(){
        Movie movie1 = new Movie(true,"backdropPath",genres,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDateTest",
                "title",false,3,4);

        String expectedString = """
                 |__________________
                  title: title
                  vote average: 3.0
                  release year: rele
                __________________________
                """;

        assertThat(movie1.toString()).isEqualTo(expectedString);
    }

    @Test
    void testToJsonFormat() {
        Movie movie = new Movie(
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
        );

        String expectedJson = """
                \s   {
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
                    }""";

        assertEquals(expectedJson, movie.toJsonFormat());
    }
}
