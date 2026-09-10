package moviesapp.controller.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import moviesapp.model.api.Genres;
import moviesapp.model.api.SearchCriteria;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.exceptions.*;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movies;
import moviesapp.viewer.buttons.ClearButton;
import moviesapp.model.movies.Movie;
import moviesapp.viewer.left_panel.LeftPanelView;
import moviesapp.viewer.left_panel.WithTitlePanelView;
import moviesapp.viewer.left_panel.WithoutTitlePanelView;
import moviesapp.viewer.new_windows.AskToConfirmClearWindow;
import moviesapp.viewer.new_windows.DetailsWindow;
import moviesapp.viewer.right_panel.ImagePanelView;
import moviesapp.viewer.right_panel.RightPanelView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static moviesapp.model.api.Genres.fillGENRE_NAME_ID_MAP;
import static moviesapp.model.api.TheMovieDbAPI.*;
import static moviesapp.model.json.JsonReader.*;
import static moviesapp.model.json.JsonWriter.FAVORITES_WRITER;
import static moviesapp.model.movies.Favorites.asMovies;

public class AppController implements Initializable {

    private WithTitlePanelView withTitlePanelViewComponent;
    private WithoutTitlePanelView withoutTitlePanelViewComponent;
    private static ImagePanelView imagePanelViewComponent;
    private static DetailsWindow currentDetailsWindow;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        genreSelectorSetUp();
        turnOnSearchWithTitleMode();
        setGUIComponents();
        appTitleButtonClicked();
    }

    /**
     * Sets up the genre selector.
     */
    private void genreSelectorSetUp() {
        fillGENRE_NAME_ID_MAP();
        ObservableList<String> genres = FXCollections.observableArrayList(Genres.instance.genreList());
        genreListView.setItems(genres);
        genreListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Sets up the GUI components.
     */
    private void setGUIComponents() {
        new LeftPanelView(mainAnchorPane, leftPane, appTitlePane, appTitleButton, selectModePane, withTitleButton, withoutTitleButton);

        withTitlePanelViewComponent = new WithTitlePanelView
                (leftPane, appTitleButton, titleAndSearchPane, title, searchBar, yearPane, yearLabel,yearField,
                favoritesWithTitlePane, favoritesWithTitleButton, goWithTitlePane, goWithTitleButton);

        withoutTitlePanelViewComponent = new WithoutTitlePanelView
                (leftPane, appTitleButton, yearsPane, years, from, minYearField,
                to, maxYearField, genresPane, genres, ratingPane, rating, atLeast,
                ratingField, searchBar, favoritesWithoutTitlePane, favoritesWithoutTitleButton,
                goWithoutTitlePane, goWithoutTitleButton, genreListView);

        new RightPanelView(leftPane, mainAnchorPane, rightStackPane, rightScrollPane);

        imagePanelViewComponent = new ImagePanelView(gridPane, rightScrollPane, specificPageField, pageManagementBox);

        new ClearButton(clearWithTitlePane, clearWithTitleButton, leftPane, withTitlePane);
        new ClearButton(clearWithoutTitlePane, clearWithoutTitleButton, leftPane, withoutTitlePane);

        clearWithTitleButton.setVisible(false);
        clearWithoutTitleButton.setVisible(false);
    }

    /**
     * Turns on the search with title mode.
     */
    @FXML
    private void turnOnSearchWithTitleMode() {
        withTitlePane.setVisible(true);
        withoutTitlePane.setVisible(false);
        clearWithoutTitleButton.setVisible(false);
    }

    /**
     * Turns on the search without title mode.
     */
    @FXML
    private void turnOnSearchWithoutTitleMode() {
        withTitlePane.setVisible(false);
        withoutTitlePane.setVisible(true);
        clearWithTitleButton.setVisible(false);
    }

    /**
     * Handles the search event with title.
     */
    @FXML
    private void searchCatcherWithTitle() {

        SearchCriteria criteria = withTitlePanelViewComponent.searchCatcherWithTitle();
        boolean inputSuccess = true;
        try {
            if(criteria.title.isEmpty()){
                throw new NoTitleException();
            }
        }
        catch (NoTitleException e) {
            alterInput(searchBar, e);
            inputSuccess = false;
        }
        try {
            checkYears(criteria.minYear, criteria.maxYear);
        }
        catch (IntervalException | NotAPositiveIntegerException | NotValidYearsException e) {
            alterInput(yearField, e);
            inputSuccess = false;
        }
        if (inputSuccess) {
            try {
                searchMoviesWithCriteria(criteria);
            }
            catch (SelectModeException ignored) {
            }
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
    }

    /**
     * Modifies a TextField's appearance to indicate invalid input and displays an error alert.
     *
     * @param field The TextField with invalid input.
     * @param e The Exception representing the error.
     */
    private void alterInput(TextField field, Exception e) {
        field.setStyle(getAlertStyle());
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Handles the search event without title.
     */
    @FXML
    private void searchCatcherWithoutTitle() {

        SearchCriteria criteria = withoutTitlePanelViewComponent.searchCatcherWithoutTitle();
        boolean inputSuccess = true;
        try {
            checkYears(criteria.minYear, criteria.maxYear);
        }
        catch (IntervalException | NotAPositiveIntegerException | NotValidYearsException e) {
            alterTwoInputs(minYearField, maxYearField, e);
            inputSuccess = false;
        }
        try {
            checkMinVoteAverage(criteria.minVoteAverage);
        }
        catch (IntervalException | NotAPositiveIntegerException e) {
            alterInput(ratingField, e);
            inputSuccess = false;
        }
        if (inputSuccess) {
            try {
                searchMoviesWithCriteria(criteria);
            }
            catch (SelectModeException ignored) {
            }
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
    }

    /**
     * Modifies two TextField object appearance to indicate invalid inputs and displays an error alert.
     *
     * @param e The Exception representing the error.
     */
    private void alterTwoInputs(TextField field1, TextField field2, Exception e) {
        field1.setStyle(getAlertStyle());
        alterInput(field2, e);
    }

    /**
     * Returns the CSS style string for customizing the appearance of an alert.
     *
     * @return The CSS style string for an alert's appearance.
     */
    private String getAlertStyle() {
        return "-fx-background-color: #d7982f; -fx-prompt-text-fill: #0f0d13;";
    }

    /**
     * Updates the image panel view with favorite movies and makes the clear button visible. (with title view)
     */
    @FXML
    private void favoritesWithTitleButtonClicked() {
        updateImagePanelView(readMoviesInReverseOrder(asMovies()));
        clearWithTitleButton.setVisible(true);
        pageManagementBox.setVisible(false);
    }

    /**
     * Updates the image panel view with favorite movies and makes the clear button visible. (without title view)
     */
    @FXML
    private void favoritesWithoutTitleButtonClicked() {
        updateImagePanelView(readMoviesInReverseOrder(asMovies()));
        clearWithoutTitleButton.setVisible(true);
        pageManagementBox.setVisible(false);
    }

    private static Movies readMoviesInReverseOrder(Movies movies) {

        List<Movie> movieList = movies.getMovieList();
        Movies moviesReverseOrder = new Movies();

        for (int i = movieList.size() - 1; i >= 0; i--) {
            moviesReverseOrder.add(movieList.get(i));
        }

        return moviesReverseOrder;
    }

    /**
     * Switches to the previous page of search results and updates the image panel view.
     */
    @FXML
    private void previousPage() {
        try {
            TheMovieDbAPI.switchToPreviousPage();
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
        catch (NoPreviousPageException ignored) {
        }
    }

    /**
     * Switches to the next page of search results and updates the image panel view.
     */
    @FXML
    private void nextPage() {
        try {
            TheMovieDbAPI.switchToNextPage();
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
        catch (NoNextPageException ignored) {
        }
    }

    @FXML
    private void specificPage() {
        try {
            TheMovieDbAPI.switchToSpecificPage(specificPageField.getText().trim());
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
        catch(NotAPositiveIntegerException | IntervalException e) {
            alterInput(specificPageField, e);
        }
    }

    /**
     * Updates the image panel view with the provided list of movies.
     *
     * @param movies The list of movies to be displayed in the image panel view.
     */
    @FXML
    private static void updateImagePanelView(Movies movies) {
        imagePanelViewComponent.distributeImages(movies);
    }

    /**
     * Handles the click event on an image by displaying details of the corresponding movie.
     *
     * @param movie The movie whose details are to be displayed.
     */
    @FXML
    public static void handleClickOnImage(Movie movie) {
        currentDetailsWindow = new DetailsWindow(movie);
    }

    /**
     * Handles the click event on the application title button.
     * Retrieves popular movies and updates the image panel view.
     */
    @FXML
    private void appTitleButtonClicked() {
        TheMovieDbAPI.popularMoviesFirstPage();
        updateImagePanelView(SEARCH_READER.findAllMovies());
    }

    /**
     * Handles the click event on the remove button by removing the specified movie from favorites.
     * Applies modifications to the favorites.
     *
     * @param movie The movie to be removed from favorites.
     */
    @FXML
    public static void removeButtonClicked(Movie movie) {
        Favorites.remove(movie);
        applyFavoritesModifications();
    }

    /**
     * Handles the click event on the add button by adding the specified movie to favorites.
     * Applies modifications to the favorites.
     *
     * @param movie The movie to be added to favorites.
     */
    @FXML
    public static void addButtonClicked(Movie movie) {
        Favorites.add(movie);
        applyFavoritesModifications();
    }

    /**
     * Applies modifications to the favorites by updating the image panel view and saving changes.
     */
    private static void applyFavoritesModifications() {
        updateImagePanelView(readMoviesInReverseOrder(asMovies()));
        currentDetailsWindow.globalStage.close();
        FAVORITES_WRITER.saveFavorites(asMovies());
    }

    /**
     * Create a new window in which a confirmation is required to clear the list of favorites.
     */
    @FXML
    public static void openClearConfirmationWindow() {
        new AskToConfirmClearWindow();
    }

    /**
     * Closes the clear confirmation window.
     *
     * @param globalStage The stage of the confirmation window to be closed.
     */
    @FXML
    public static void closeClearConfirmationWindow(Stage globalStage) {
        globalStage.close();
    }

    /**
     * Handles the click event on the continue button, clears favorites, closes confirmation window,
     * and updates the image panel view with cleared favorites.
     *
     * @param globalStage The stage of the confirmation window.
     */
    @FXML
    public static void continueButtonClicked(Stage globalStage) {
        Favorites.clear();
        FAVORITES_WRITER.clear();
        closeClearConfirmationWindow(globalStage);
        updateImagePanelView(Favorites.asMovies());
    }




    /////////////////////////////////////////////////////////// HEAD FXML Identifiers
    public AnchorPane mainAnchorPane;
    public Pane leftPane;
    public Button appTitleButton;
    public Pane titleAndSearchPane;
    public Label title;
    public TextField searchBar;
    public Label years;
    public Label from;
    public Label to;
    public TextField minYearField;
    public TextField maxYearField;
    public Pane yearsPane;
    public Pane genresPane;
    public Pane ratingPane;
    public Label genres;
    public Label rating;
    public Label atLeast;
    public TextField ratingField;
    public Button goWithoutTitleButton;
    public Button favoritesWithoutTitleButton;
    public ScrollPane rightScrollPane;
    public StackPane rightStackPane;
    public Pane yearPane;
    public Label yearLabel;
    public TextField yearField;
    public Pane selectModePane;
    public Button withTitleButton;
    public Button withoutTitleButton;
    public Pane favoritesWithTitlePane;
    public Pane goWithTitlePane;
    public Button favoritesWithTitleButton;
    public Button goWithTitleButton;
    public Pane withTitlePane;
    public Pane withoutTitlePane;
    public ListView<String> genreListView;
    public GridPane gridPane;
    public Pane goWithoutTitlePane;
    public Pane favoritesWithoutTitlePane;
    public Pane clearWithTitlePane;
    public Pane clearWithoutTitlePane;
    public Button clearWithTitleButton;
    public Button clearWithoutTitleButton;
    public Pane appTitlePane;
    public Button specificPageButton;
    public TextField specificPageField;
    public HBox pageManagementBox;

    /////////////////////////////////////////////////////////// END FXML Identifiers
}
