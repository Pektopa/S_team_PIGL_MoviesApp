package moviesapp.viewer;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class ClearButtonTest {

    private volatile boolean uiInitialized = false;

    @Start
    private void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClearButtonGUI.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        // Mark UI as initialized once it's shown
        uiInitialized = true;
    }
    @Test
    public void testOpenClearConfirmationWindowAndContinueButtonClicked(FxRobot robot) throws InterruptedException {
        waitForUIInitialization();
        Platform.runLater(() -> robot.clickOn("#clearButton"));
        verifyThat("#clearConfirmationAnchorPane", isVisible());
        verifyThat("#confirmation", hasText("Do you want to continue?"));
        Platform.runLater(() -> robot.clickOn("#ContinueButton"));
        assertThat(Favorites.instance.isEmpty()).isTrue();
    }

    @Test
    public void testOpenClearConfirmationWindowAndClearButtonClicked(FxRobot robot) throws InterruptedException {
        waitForUIInitialization();
        List<String> genres = new ArrayList<>();
        Favorites.instance.add(new Movie(false, "",genres, "2","",
                "","",0.0,"","","",false,
                0.0,0));
        Platform.runLater(() -> robot.clickOn("#clearButton"));
        verifyThat("#clearConfirmationAnchorPane", isVisible());
        verifyThat("#confirmation", hasText("Do you want to continue?"));
        Platform.runLater(() -> robot.clickOn("#CancelButton"));
        assertThat(Favorites.instance.isEmpty()).isFalse();
    }

    // Utility method to wait for UI initialization
    private void waitForUIInitialization() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        while (!uiInitialized) {
            latch.await(100, TimeUnit.MILLISECONDS); // Sleep for a short duration
        }
    }
}