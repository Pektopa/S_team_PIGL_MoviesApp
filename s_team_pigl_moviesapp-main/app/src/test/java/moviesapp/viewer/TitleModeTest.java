package moviesapp.viewer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Objects;

import static moviesapp.App.minHeight;
import static moviesapp.App.minWidth;
import static org.testfx.api.FxAssert.verifyThat;

public class TitleModeTest extends ApplicationTest {
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
    public void titleButtonTest() {
        verifyThat("#withTitlePane", Node::isVisible);
        clickOn("#withoutTitleButton");
        verifyThat("#withoutTitlePane",Node::isVisible);
        clickOn("#withTitleButton");
        verifyThat("#withTitlePane", Node::isVisible);
    }
}
