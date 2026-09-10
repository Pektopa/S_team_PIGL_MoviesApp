package moviesapp.viewer.new_windows;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import moviesapp.model.api.RequestBuilder;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movie;

import static moviesapp.controller.GUI.AppController.addButtonClicked;
import static moviesapp.controller.GUI.AppController.removeButtonClicked;

public class DetailsWindow {

    @FXML public Stage globalStage;
    @FXML public Button addButton;
    @FXML public Button removeButton;
    @FXML public AnchorPane movieDetailsAnchorPane;
    @FXML public ImageView imageView;
    @FXML public Label titleLabelAndYear;
    @FXML public Label usefulInformationLabel;
    @FXML public Label popularityLabel;
    @FXML public Label voteAverageLabel;
    @FXML public Label overviewLabel;
    @FXML public TextFlow textFlow;

    public DetailsWindow(Movie movie){
        showDetails(movie);
    }

    public void showDetails(Movie movie){
        globalStage = new Stage();
        globalStage.setMinWidth(900);
        globalStage.setMinHeight(500);
        initTextFlow(movie);

        initMovieDetailsAnchorPane();

        initImageView();
        Image movieImage = new Image(RequestBuilder.imageBaseURL + RequestBuilder.imageSize + movie.posterPath());
        imageView.setImage(movieImage);

        initTitleLabelAndYear(movie);
        initUsefulInformationLabel(movie);
        initPopularityLabel(movie);
        initVoteAverageLabel(movie);
        initOverviewLabel();

        if(Favorites.contains(movie)){
            initRemoveButton(movie);
            movieDetailsAnchorPane.getChildren().add(removeButton);
        }
        else{
            initAddButton(movie);
            movieDetailsAnchorPane.getChildren().add(addButton);
        }

        movieDetailsAnchorPane.getChildren().addAll(imageView,titleLabelAndYear,usefulInformationLabel,popularityLabel,voteAverageLabel,overviewLabel,textFlow);

        movieDetailsAnchorPane.setVisible(true);
        Scene globalScene = new Scene(movieDetailsAnchorPane, 900, 500);
        globalStage.setTitle("Details - MovieLand");
        globalStage.setScene(globalScene);
        globalStage.show();
    }

    /**
     * Initialise movieDetailsAnchorPane
     */
    private void initMovieDetailsAnchorPane(){
        movieDetailsAnchorPane = new AnchorPane();
        movieDetailsAnchorPane.prefHeight(500);
        movieDetailsAnchorPane.prefWidth(900);
        movieDetailsAnchorPane.setId("movieDetailsAnchorPane");
        movieDetailsAnchorPane.setStyle("-fx-background-image: url('/viewer/images/background-pattern.png'); " +
                "-fx-background-size: cover; -fx-border-color: #e5e5e5; -fx-effect: dropshadow(gaussian, #9d36f7, 15, 0.1, 0, 0)");
    }

    /**
     * Initialise imageView
     */
    private void initImageView(){
        imageView = new ImageView();
        imageView.setLayoutX(20);
        imageView.setLayoutY(20);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, #9d36f7, 15, 0.1, 0, 0)");
    }

    /**
     * Initialise the title and the release year of the movie
     * @param movie the movie of which we want the details
     */
    private void initTitleLabelAndYear(Movie movie){
        titleLabelAndYear = new Label(movie.title() + "(" + movie.getReleaseYear() + ")");
        titleLabelAndYear.setLayoutX(255);
        titleLabelAndYear.setLayoutY(-3);
        titleLabelAndYear.setTextFill(Paint.valueOf("white"));
        setGlobalStageWidth(titleLabelAndYear);
        titleLabelAndYear.setStyle("-fx-font-family: 'Source Sans Pro'; -fx-font-size: 70px; -fx-font-weight: bold;");
    }

    /**
     * Initialise usefulInformationLabel
     * @param movie the movie of which we want the details
     */
    private void initUsefulInformationLabel(Movie movie){
        usefulInformationLabel = new Label(movie.releaseDate() + " (" + movie.originalLanguage() + ") " + movie.genresToString());
        usefulInformationLabel.setLayoutX(258);
        usefulInformationLabel.setLayoutY(80);
        usefulInformationLabel.setTextFill(Paint.valueOf("white"));
        setGlobalStageWidth(usefulInformationLabel);
        usefulInformationLabel.setStyle("-fx-font-family: 'Source Sans Pro'; -fx-font-size: 20px; -fx-font-weight: light;");
    }

    /**
     * Initialise popularityLabel
     * @param movie the movie of which we want the details
     */
    private void initPopularityLabel(Movie movie){
        popularityLabel = new Label("Popularity: "+ movie.popularity());
        popularityLabel.setLayoutX(258);
        popularityLabel.setLayoutY(120);
        popularityLabel.setTextFill(Paint.valueOf("white"));
        setGlobalStageWidth(popularityLabel);
        popularityLabel.setStyle("-fx-font-family: 'Source Sans Pro'; -fx-font-size:30px;");
    }

    /**
     * Initialise voteAverageLabel
     * @param movie the movie of which we want the details
     */
    private void initVoteAverageLabel(Movie movie){
        voteAverageLabel = new Label("Vote Average: "+ movie.minVoteAverage());
        voteAverageLabel.setLayoutX(258);
        voteAverageLabel.setLayoutY(160);
        voteAverageLabel.setTextFill(Paint.valueOf("white"));
        setGlobalStageWidth(voteAverageLabel);
        voteAverageLabel.setStyle("-fx-font-family: 'Soure Sans Pro'; -fx-font-size:30px;");
    }

    /**
     * Initialise overviewLabel
     */
    private void initOverviewLabel(){
        overviewLabel = new Label("Overview:");
        overviewLabel.setLayoutX(258);
        overviewLabel.setLayoutY(210);
        overviewLabel.setTextFill(Paint.valueOf("white"));
        setGlobalStageWidth(overviewLabel);
        overviewLabel.setStyle("-fx-font-family: 'Source Sans Pro'; -fx-font-size:35px; -fx-font-weight: bold;");
    }

    /**
     * Initialise textFlow
     * @param movie the movie of which we want the details
     */
    private void initTextFlow(Movie movie){
        textFlow = new TextFlow();
        Text overviewContentText = new Text(movie.overviewToString(72));
        textFlow.setLayoutX(258);
        textFlow.setLayoutY(250);
        textFlow.prefWidth(60);
        textFlow.prefHeight(20);
        textFlow.heightProperty().addListener((observable, oldValue, newValue) -> globalStage.setHeight(250 + newValue.doubleValue() + 100));
        overviewContentText.setTextAlignment(TextAlignment.JUSTIFY);
        overviewContentText.setFill(Paint.valueOf("white"));
        overviewContentText.setStyle("-fx-font-family: 'Source Sans Pro'; -fx-font-size:20px; -fx-font-weight: light;");
        textFlow.getChildren().add(overviewContentText);
    }

    /**
     * Initialise the remove button
     * @param movie the movie of which we want the details
     */
    private void initRemoveButton(Movie movie){
        removeButton = new Button("Remove From\nFavorites");
        removeButton.setLayoutX(20);
        removeButton.setLayoutY(390);
        removeButton.setPrefWidth(220);
        removeButton.setPrefHeight(60);
        removeButton.setTextAlignment(TextAlignment.CENTER);
        removeButton.setStyle("-fx-font-family: 'GROBOLD';-fx-font-size: 20px; -fx-text-fill: #e5e5e5; -fx-background-color: #E50914;");
        removeButton.setOnMouseExited(event -> removeButton.setStyle("-fx-font-family: 'GROBOLD';-fx-font-size: 20px; -fx-text-fill: #e5e5e5; -fx-background-color: #E50914;"));
        removeButton.setOnMouseEntered(event -> removeButton.setStyle("-fx-font-family: 'GROBOLD';-fx-font-size: 20px; -fx-background-color: #e5e5e5; -fx-text-fill: #E50914; -fx-cursor: hand;"));
        removeButton.setOnAction(event -> removeButtonClicked(movie));
    }


    /**
     * Initialise the add button
     * @param movie the movie of which we want the details
     */
    private void initAddButton(Movie movie){
        addButton = new Button("Add To Favorites");
        addButton.setLayoutX(20);
        addButton.setLayoutY(390);
        addButton.setPrefWidth(220);
        addButton.setPrefHeight(60);
        addButton.setTextAlignment(TextAlignment.CENTER);
        addButton.setStyle("-fx-font-family: 'GROBOLD';-fx-font-size: 20px; -fx-text-fill: #e5e5e5; -fx-background-color: #E50914;");
        addButton.setOnMouseExited(event -> addButton.setStyle("-fx-font-family: 'GROBOLD';-fx-font-size: 20px; -fx-text-fill: #e5e5e5; -fx-background-color: #E50914;"));
        addButton.setOnMouseEntered(event -> addButton.setStyle("-fx-font-family: 'GROBOLD';-fx-font-size: 20px; -fx-background-color: #e5e5e5; -fx-text-fill: #E50914; -fx-cursor: hand;"));
        addButton.setOnAction(event -> addButtonClicked(movie) );
    }

    private void setGlobalStageWidth(Label label){
        label.widthProperty().addListener((observable, oldValue, newValue) -> globalStage.setHeight(250 + newValue.doubleValue() + 100));
    }
}
