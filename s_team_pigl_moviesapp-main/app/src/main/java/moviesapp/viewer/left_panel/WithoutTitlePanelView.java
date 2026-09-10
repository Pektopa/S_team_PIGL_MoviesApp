package moviesapp.viewer.left_panel;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import moviesapp.model.api.Genres;
import moviesapp.model.api.SearchCriteria;
import moviesapp.viewer.buttons.WithoutTitleButtons;

import java.util.List;

import static moviesapp.model.api.RequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.RequestBuilder.minAcceptableYearValue;

public class WithoutTitlePanelView {
    private final Pane leftPane;
    private final Button appTitleButton;
    private final Pane yearsPane;
    private final Label years;
    private final Label from;
    private final TextField minYearField;
    private final Label to;
    private final TextField maxYearField;
    private final Pane genresPane;
    private final Label genres;
    private final Pane ratingPane;
    private final Label rating;
    private final Label atLeast;
    private final TextField ratingField;
    private final TextField searchBar;
    private final ListView<String> genreListView;

    public WithoutTitlePanelView(Pane leftPane, Button appTitleButton, Pane yearsPane, Label years, Label from, TextField minYearField,
                                 Label to, TextField maxYearField, Pane genresPane, Label genres, Pane ratingPane, Label rating, Label atLeast,
                                 TextField ratingField, TextField searchBar, Pane favoritesPane, Button favoritesButton, Pane goPane, Button goButton, ListView<String> genreListView) {
        this.leftPane = leftPane;
        this.appTitleButton = appTitleButton;
        this.yearsPane = yearsPane;
        this.years = years;
        this.from = from;
        this.minYearField = minYearField;
        this.to = to;
        this.maxYearField = maxYearField;
        this.genresPane = genresPane;
        this.genres = genres;
        this.ratingPane = ratingPane;
        this.rating = rating;
        this.atLeast = atLeast;
        this.ratingField = ratingField;
        this.searchBar = searchBar;
        this.genreListView = genreListView;

        new WithoutTitleButtons(ratingPane, favoritesPane, favoritesButton, goPane, goButton);

        setupView();
    }

    private void setupView() {
        setYearsPane();
        setYears();
        setGenresPane();
        setGenres();
        setGenreListView();
        setRatingPane();
        setRating();
    }

    private void setYearsPane(){
        yearsPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(yearsPane.widthProperty().divide(2)));
        yearsPane.layoutYProperty().bind(appTitleButton.layoutYProperty().add(170));
        yearsPane.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.9));
    }

    private void setYears(){
        from.layoutXProperty().bind(years.layoutXProperty());
        from.layoutYProperty().bind(years.layoutYProperty().add(40));

        minYearField.layoutXProperty().bind(searchBar.layoutXProperty());
        minYearField.layoutYProperty().bind(from.layoutYProperty().subtract(7));
        minYearField.setPromptText(String.valueOf(minAcceptableYearValue));
        minYearField.setPrefWidth(70);

        to.layoutXProperty().bind(minYearField.layoutXProperty().add(83));
        to.layoutYProperty().bind(from.layoutYProperty());

        maxYearField.layoutXProperty().bind(to.layoutXProperty().add(32));
        maxYearField.layoutYProperty().bind(to.layoutYProperty().subtract(7));
        maxYearField.setPromptText(String.valueOf(maxAcceptableYearValue));
        maxYearField.setPrefWidth(70);
    }

    private void setGenresPane(){
        genresPane.layoutXProperty().bind(yearsPane.layoutXProperty());
        genresPane.layoutYProperty().bind(yearsPane.layoutYProperty().add(100));
        genresPane.prefWidthProperty().bind(yearsPane.prefWidthProperty().divide(2).subtract(10));
    }

    private void setGenres(){
        genres.layoutXProperty().bind(from.layoutXProperty());
    }

    private void setGenreListView(){
        genreListView.layoutXProperty().bind(genres.layoutXProperty());
        genreListView.layoutYProperty().bind(genres.layoutYProperty().add(35));
        genreListView.prefWidthProperty().bind(genresPane.widthProperty());
        genreListView.setPrefHeight(178);
        genreListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    private void setRatingPane(){
        ratingPane.layoutXProperty().bind(genresPane.layoutXProperty().add(genresPane.widthProperty().add(50)));
        ratingPane.layoutYProperty().bind(genresPane.layoutYProperty());
        ratingPane.prefWidthProperty().bind(genresPane.prefWidthProperty());
    }

    private void setRating(){
        atLeast.layoutXProperty().bind(rating.layoutXProperty());
        atLeast.layoutYProperty().bind(rating.layoutYProperty().add(30));

        ratingField.layoutXProperty().bind(atLeast.layoutXProperty().add(89));
        ratingField.layoutYProperty().bind(atLeast.layoutYProperty().subtract(7));
        ratingField.setPrefWidth(85);
        ratingField.setPromptText("0 to 10");
    }

    public SearchCriteria searchCatcherWithoutTitle(){

        minYearField.setStyle(getFieldStyle());
        maxYearField.setStyle(getFieldStyle());
        ratingField.setStyle(getFieldStyle());

        List<String> selectedGenresId = Genres.genresToGenreIds(genreListView.getSelectionModel().getSelectedItems());

        return new SearchCriteria("", minYearField.getText().trim(), maxYearField.getText().trim(),
                selectedGenresId, ratingField.getText().trim(), "1");
    }

    public static String getFieldStyle() {
        return "-fx-background-color: #0f0d13; -fx-prompt-text-fill: #b2b2b2";
    }
}
