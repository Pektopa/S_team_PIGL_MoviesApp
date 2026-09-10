package moviesapp.viewer.buttons;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

import static moviesapp.controller.GUI.AppController.*;

public class ClearButton {
    private final Button clearButton;
    private final Pane leftPane;
    private final Pane withTitlePane;
    private final Pane clearPane;

    public ClearButton(Pane clearPane, Button clearButton, Pane leftPane, Pane withTitlePane){
        this.clearButton = clearButton;
        this.leftPane = leftPane;
        this.withTitlePane = withTitlePane;
        this.clearPane = clearPane;

        setView();
    }

    private void setView(){
        setClearPane();
        setClearButton();
    }

    private void setClearPane(){
        clearPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(clearButton.widthProperty().divide(2)));
        clearPane.layoutYProperty().bind(withTitlePane.heightProperty().add(40));
    }

    private void setClearButton(){
        clearButton.setOnAction(event -> openClearConfirmationWindow());
        clearButton.setTextAlignment(TextAlignment.CENTER);
        clearButton.setPrefWidth(200);
        clearButton.setPrefHeight(26);
    }
}
