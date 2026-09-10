package moviesapp.viewer.right_panel;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;

import java.util.Objects;

import static moviesapp.controller.GUI.AppController.handleClickOnImage;
import static moviesapp.model.api.RequestBuilder.imageBaseURL;
import static moviesapp.model.api.RequestBuilder.imageSize;
import static moviesapp.model.json.JsonReader.SEARCH_READER;
import static moviesapp.viewer.left_panel.WithoutTitlePanelView.getFieldStyle;
import static moviesapp.viewer.right_panel.RightPanelView.rightScrollPanePadding;

public class ImagePanelView {
    private final ScrollPane rightScrollPane;
    private final GridPane gridPane;
    private final TextField specificPageField;
    private final HBox pageManagementBox;
    private final int imageWidth = 258;
    private final int numberOfImagesPerRow = 3;
    private double horizontalGap = 15;

    public ImagePanelView(GridPane gridPane, ScrollPane rightScrollPane, TextField specificPageField, HBox pageManagementBox) {
        this.gridPane = gridPane;
        this.rightScrollPane = rightScrollPane;
        this.specificPageField = specificPageField;
        this.pageManagementBox = pageManagementBox;

        setupView();
    }

    public void setupView() {
        setGridPane();
        setPageManagementBox();
        setSpecificPageField();
    }

    private void setGridPane() {
        gridPane.prefWidthProperty().bind(rightScrollPane.widthProperty());
        gridPane.prefHeightProperty().bind(rightScrollPane.heightProperty());
        gridPane.setPadding(new Insets(0, 0, rightScrollPanePadding, 0));
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        adjustHorizontalGap();

        int verticalGap = 50;
        gridPane.setVgap(verticalGap);
    }

    private void adjustHorizontalGap() {
        rightScrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double containerWidth = newVal.doubleValue() - (rightScrollPanePadding * 2);
            double totalGapWidth = containerWidth - (imageWidth * numberOfImagesPerRow);
            int numberOfGaps = numberOfImagesPerRow - 1 + 2;
            horizontalGap = totalGapWidth / numberOfGaps;
            gridPane.setHgap(horizontalGap);
        });
    }

    private void clearImageDisplay(){
        gridPane.getChildren().clear();
    }

    public void distributeImages(Movies movies) {
        clearImageDisplay();
        rightScrollPane.setVvalue(0.0);

        int row = 0, col = 0;

        if(movies == null || movies.isEmpty()){
            pageManagementBox.setVisible(false);

            Image image = new Image(Objects.requireNonNull(getClass().getResource("/viewer/images/no-movie-found.jpg")).toExternalForm());

            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(imageWidth);
            gridPane.add(imageView, 0, 0);
            return;
        }

        pageManagementBox.setVisible(true);
        for(Movie movie : movies) {
            String moviePosterPath = movie.posterPath();
            Image image;
            if(moviePosterPath.equals("null")) {
                image = new Image(Objects.requireNonNull(getClass().getResource("/viewer/images/poster-unavailable.jpg")).toExternalForm());
            }
            else {
                image = new Image(imageBaseURL + imageSize + moviePosterPath);
            }
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(imageWidth);

            imageView.setOnMouseClicked(event -> handleClickOnImage(movie));

            gridPane.add(imageView, col, row);

            col++;
            if(col >= numberOfImagesPerRow) {
                col = 0;
                row++;
            }
        }
    }

    private void setPageManagementBox() {
        pageManagementBox.setPadding(new Insets(0, 0, rightScrollPanePadding, 0));
    }

    private void setSpecificPageField() {
        gridPane.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    specificPageField.setStyle(getFieldStyle());
                    specificPageField.setPromptText(SEARCH_READER.getPageInJson() + "/" + SEARCH_READER.numberOfPagesOfMoviesInJson());
                    specificPageField.setText("");
                }
            }
        });
    }
}
