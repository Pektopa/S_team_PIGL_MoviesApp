package moviesapp.viewer.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class FavoritesWithTitleButton {
    private final Pane favoritesPane;
    private final Pane yearPane;
    private final Button favoritesButton;

    public FavoritesWithTitleButton(Pane favoritesPane, Pane yearPane, Button favoritesButton){
        this.favoritesPane = favoritesPane;
        this.yearPane = yearPane;
        this.favoritesButton = favoritesButton;

        setView();
    }

    private void setView(){
        setFavoritesPane();
        setFavoritesButton();
    }

    private void setFavoritesPane(){
        favoritesPane.layoutXProperty().bind(yearPane.layoutXProperty().add(280));
        favoritesPane.layoutYProperty().bind(yearPane.layoutYProperty().add(60));
    }

    private void setFavoritesButton(){
        favoritesButton.setPrefHeight(26);
        favoritesButton.setPrefWidth(140);
    }
}
