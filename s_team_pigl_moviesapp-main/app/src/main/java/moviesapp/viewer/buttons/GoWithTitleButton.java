package moviesapp.viewer.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class GoWithTitleButton {
    private final Pane goPane;
    private final Pane yearPane;
    private final Button goButton;

    public GoWithTitleButton(Pane goPane, Pane yearPane, Button goButton){
        this.goPane = goPane;
        this.yearPane = yearPane;
        this.goButton = goButton;

        setView();
    }

    private void setView(){
        setGoPane();
        setGoButton();
    }

    private void setGoPane(){
        goPane.layoutXProperty().bind(yearPane.layoutXProperty().add(280));
        goPane.layoutYProperty().bind(yearPane.layoutYProperty().add(6));
    }

    private void setGoButton(){
        goButton.setPrefHeight(26);
        goButton.setPrefWidth(80);
    }
}
