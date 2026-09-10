package moviesapp.viewer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import moviesapp.model.json.JsonReader;
import moviesapp.model.json.JsonWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Objects;

import static moviesapp.App.minHeight;
import static moviesapp.App.minWidth;

public class SearchTest extends ApplicationTest {
    private static JsonReader jsonReader;
    static String apiJsonPath = System.getProperty("user.dir") + "/src/test/resources/search.json";


    @BeforeAll
    static void setupBeforeAll() {
        new JsonWriter(System.getProperty("user.dir") + "/src/test/resources/search.json").clear();
        updateJsonReader();
    }

    public static void updateJsonReader(){
        jsonReader = new JsonReader(apiJsonPath);
    }

    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/viewer/fonts/Watched.ttf"), 20);
        Font.loadFont(getClass().getResourceAsStream("/viewer/fonts/GROBOLD.ttf"), 20);
        Font.loadFont(getClass().getResourceAsStream("/viewer/fonts/SourceSansPro-Bold.otf"), 20);
        Font.loadFont(getClass().getResourceAsStream("/viewer/fonts/SourceSansPro-Light.otf"), 20);
        Font.loadFont(getClass().getResourceAsStream("/viewer/fonts/SourceSansPro-Regular.otf"), 20);
        Font.loadFont(getClass().getResourceAsStream("/viewer/fonts/SourceSansPro-Semibold.otf"), 20);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/viewer/moviesapp.fxml")));

        primaryStage.setTitle("MoviesApp");
        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);
        primaryStage.centerOnScreen();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    public void searchWithTitleTest(){
        clickOn("#searchBar");
        write("Mario");
        clickOn("#yearField");
        write("2013");
        clickOn("#goButtonWithTitle");
        updateJsonReader();

        assert(!jsonReader.findAllMovies().isEmpty());
    }

    @Test
    public void searchWithoutTittleTest(){
        clickOn("#withoutTitleButton");
        clickOn("#singleOrMinYearField");
        write("2012");
        clickOn("#maxYearField");
        write("2020");
        clickOn("#ratingField");
        write("3");
        clickOn("#goButtonWithoutTitle");
        updateJsonReader();

        assert(!jsonReader.findAllMovies().isEmpty());
    }

}
