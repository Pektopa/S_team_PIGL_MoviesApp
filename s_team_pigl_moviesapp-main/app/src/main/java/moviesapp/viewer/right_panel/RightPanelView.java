package moviesapp.viewer.right_panel;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class RightPanelView {
    private final Pane leftPane;
    private final AnchorPane mainAnchorPane;
    private final StackPane rightStackPane;
    private final ScrollPane rightScrollPane;
    static final int rightScrollPanePadding = 50;

    public RightPanelView(Pane leftPane, AnchorPane mainAnchorPane, StackPane rightStackPane, ScrollPane rightScrollPane) {
        this.leftPane = leftPane;
        this.mainAnchorPane = mainAnchorPane;
        this.rightStackPane = rightStackPane;
        this.rightScrollPane = rightScrollPane;

        setupView();
    }

    private void setupView() {
        setRightStackPane();
        setRightScrollPane();
    }

    private void setRightStackPane(){
        leftPane.widthProperty().addListener((observable, oldValue, newValue) -> rightStackPane.layoutXProperty().setValue(newValue));

        rightStackPane.prefWidthProperty().bind(mainAnchorPane.widthProperty().subtract(leftPane.widthProperty()));
        rightStackPane.prefHeightProperty().bind(mainAnchorPane.heightProperty());
    }

    private void setRightScrollPane(){
        double sidePaddingMul = 1;
        rightScrollPane.setPadding(new Insets(rightScrollPanePadding, rightScrollPanePadding * sidePaddingMul, 0, rightScrollPanePadding * sidePaddingMul));
        rightScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightScrollPane.setFitToWidth(true);
    }
}
