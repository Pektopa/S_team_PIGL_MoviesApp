package moviesapp.viewer.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class SelectSearchModeButtons {

    private final Pane selectModePane;
    private final Button withTileButton;
    private final Button withoutTitleButton;
    private final Pane leftPane;
    private final Button appTitleButton;

    public SelectSearchModeButtons(Pane selectModePane, Button withTileButton, Button withoutTitleButton, Pane leftPane, Button appTitleButton){
        this.selectModePane = selectModePane;
        this.withTileButton = withTileButton;
        this.withoutTitleButton = withoutTitleButton;
        this.leftPane = leftPane;
        this.appTitleButton = appTitleButton;

        setView();
    }

    private void setView(){
        setSelectModePane();
        setWithTileButton();
        setWithoutTitleButton();
    }

    private void setSelectModePane(){
        selectModePane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(selectModePane.widthProperty().divide(2)));
        selectModePane.layoutYProperty().bind(appTitleButton.layoutYProperty().add(95));
    }

    private void setWithTileButton(){
        withTileButton.setPrefHeight(26);
        withTileButton.setPrefWidth(140);
    }

    private void setWithoutTitleButton(){
        withoutTitleButton.layoutXProperty().bind(withTileButton.layoutXProperty().add(160));
        withoutTitleButton.setPrefHeight(26);
        withoutTitleButton.setPrefWidth(170);
    }
}
