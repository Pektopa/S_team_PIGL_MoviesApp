package moviesapp.viewer.left_panel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import moviesapp.model.api.SearchCriteria;

import moviesapp.viewer.buttons.FavoritesWithTitleButton;
import moviesapp.viewer.buttons.GoWithTitleButton;

import java.util.ArrayList;

import static moviesapp.model.api.RequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.RequestBuilder.minAcceptableYearValue;
import static moviesapp.viewer.left_panel.WithoutTitlePanelView.getFieldStyle;

public class WithTitlePanelView {
    private final Pane leftPane;
    private final Button appTitleButton;
    private final Pane titleAndSearchPane;
    private final Label title;
    private final TextField searchBar;
    private final Pane yearPane;
    private final Label year;
    private final TextField yearField;

    public WithTitlePanelView(Pane leftPane, Button appTitleButton, Pane titleAndSearchPane, Label title, TextField searchBar,
                              Pane yearPane, Label year, TextField yearField, Pane favoritesPane, Button favoritesButton,
                              Pane goPane, Button goButton) {

        this.leftPane = leftPane;
        this.appTitleButton = appTitleButton;
        this.titleAndSearchPane = titleAndSearchPane;
        this.title = title;
        this.searchBar = searchBar;
        this.yearPane = yearPane;
        this.year = year;
        this.yearField = yearField;

        new FavoritesWithTitleButton(favoritesPane, yearPane, favoritesButton);
        new GoWithTitleButton(goPane, yearPane, goButton);

        setupView();
    }

    private void setupView() {
        setTitleAndSearchPane();
        setTitle();
        setSearchBar();
        setYearPane();
        setYear();
    }

    private void setTitleAndSearchPane(){
        titleAndSearchPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(titleAndSearchPane.widthProperty().divide(2)));
        titleAndSearchPane.layoutYProperty().bind(appTitleButton.layoutYProperty().add(170));
        titleAndSearchPane.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.9));
    }

    private void setTitle(){
        title.layoutYProperty().bind(titleAndSearchPane.heightProperty().divide(2).subtract(title.heightProperty().divide(2)));
    }

    private void setSearchBar(){
        searchBar.layoutXProperty().bind(title.layoutXProperty().add(60));
        searchBar.layoutYProperty().bind(title.layoutYProperty().subtract(7));
        searchBar.prefWidthProperty().bind(titleAndSearchPane.widthProperty().subtract(70));
    }

    private void setYearPane(){
        yearPane.layoutXProperty().bind(titleAndSearchPane.layoutXProperty());
        yearPane.layoutYProperty().bind(titleAndSearchPane.layoutYProperty().add(80));
        yearPane.prefWidthProperty().bind(titleAndSearchPane.prefWidthProperty());
    }

    private void setYear(){
        year.layoutYProperty().bind(yearPane.heightProperty().divide(2).subtract(year.heightProperty().divide(2)));

        yearField.setPrefWidth(190);
        yearField.layoutXProperty().bind(searchBar.layoutXProperty());
        yearField.layoutYProperty().bind(year.layoutYProperty().subtract(7));
        yearField.setPromptText("from " + minAcceptableYearValue + " to " + maxAcceptableYearValue);
    }

    public SearchCriteria searchCatcherWithTitle() {
        yearField.setStyle(getFieldStyle());
        return new SearchCriteria(searchBar.getText().trim(), yearField.getText().trim(), yearField.getText().trim(), new ArrayList<>(), "", "1");
    }
}
