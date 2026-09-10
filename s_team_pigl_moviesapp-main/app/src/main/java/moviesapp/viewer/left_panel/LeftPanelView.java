package moviesapp.viewer.left_panel;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import moviesapp.viewer.buttons.SelectSearchModeButtons;

import static moviesapp.App.minHeight;
import static moviesapp.App.minWidth;

public class LeftPanelView {

    private final AnchorPane mainAnchorPane;
    private final Pane leftPane;
    private final Pane appTitlePane;
    public SelectSearchModeButtons selectSearchModeButtons;

    public LeftPanelView(AnchorPane mainAnchorPane, Pane leftPane, Pane appTitlePane, Button appTitleButton, Pane selectModePane, Button withTitleButton, Button withoutTitleButton) {
        this.mainAnchorPane = mainAnchorPane;
        this.leftPane = leftPane;
        this.appTitlePane = appTitlePane;

        selectSearchModeButtons = new SelectSearchModeButtons(selectModePane, withTitleButton, withoutTitleButton, leftPane, appTitleButton);

        setupView();
    }

    private void setupView() {
        setMainAnchorPane();
        setLeftPane();
        setAppTitlePane();
    }

    private void setMainAnchorPane(){
        mainAnchorPane.setMinWidth(minWidth);
        mainAnchorPane.setMinHeight(minHeight);
    }

    private void setLeftPane(){
        leftPane.prefWidthProperty().bind(mainAnchorPane.widthProperty().multiply(0.34));
        leftPane.prefHeightProperty().bind(mainAnchorPane.heightProperty());
    }

    private void setAppTitlePane(){
        appTitlePane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(appTitlePane.widthProperty().divide(2)));
        appTitlePane.setLayoutY(10);
    }
}
