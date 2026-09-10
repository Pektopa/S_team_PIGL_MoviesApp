package moviesapp.model;

import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;
import moviesapp.model.json.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest {

    private final String favoritesTestPath = System.getProperty("user.dir") + "/src/test/resources/favorites-example.json";

    @Test
    public void testSaveFavorites() {
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
                6
        ));

        Movies movies = new Movies(movieList);

        JsonWriter jsonWriter = new JsonWriter(favoritesTestPath);
        jsonWriter.saveFavorites(movies);

        String expectedJson = """
                {
                  "results" : [
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
                      "vote_count" : 6
                    }
                  ],
                  "total_pages" : 1,
                  "total_results" : 2
                }
                """;
        assertEquals(expectedJson, readContentFromFavoritesTest());
    }

    private String readContentFromFavoritesTest() {
        String fileContent = null;
        StringBuilder stringBuilder = new StringBuilder();
        int character;

        try (FileReader reader = new FileReader(favoritesTestPath, StandardCharsets.UTF_8)) {

            while ((character = reader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            fileContent = stringBuilder.toString();

        } catch (IOException e) {
            System.err.println("IOException e from 'new FileReader(...)'");
        }
        return fileContent;
    }
}
