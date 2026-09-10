package moviesapp.viewer.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class WithoutTitleButtons {

    private final Pane favoritesPane;
    private final Pane goPane;
    private final Pane ratingPane;
    private final Button favoritesButton;
    private final Button goButton;

    public WithoutTitleButtons(Pane ratingPane, Pane favoritesPane, Button favoritesButton, Pane goPane, Button goButton){
        this.ratingPane = ratingPane;
        this.favoritesPane = favoritesPane;
        this.goPane = goPane;
        this.favoritesButton = favoritesButton;
        this.goButton = goButton;

        setView();
    }

    private void setView(){
        setGoPane();
        setFavoritesPane();
        setFavoritesButton();
        setGoButton();
    }


    private void setFavoritesPane(){
        favoritesPane.layoutXProperty().bind(goPane.layoutXProperty());
        favoritesPane.layoutYProperty().bind(goPane.layoutYProperty().add(54));
    }

    private void setFavoritesButton(){
        favoritesButton.setPrefHeight(26);
        favoritesButton.setPrefWidth(140);
    }

    private void setGoPane(){
        goPane.layoutXProperty().bind(ratingPane.layoutXProperty());
        goPane.layoutYProperty().bind(ratingPane.layoutYProperty().add(120));
    }

    private void setGoButton(){
        goButton.setPrefHeight(26);
        goButton.setPrefWidth(80);
    }
}
