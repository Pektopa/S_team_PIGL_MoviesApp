package moviesapp.viewer.new_windows;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import static moviesapp.controller.GUI.AppController.closeClearConfirmationWindow;
import static moviesapp.controller.GUI.AppController.continueButtonClicked;

public class AskToConfirmClearWindow {

    private Label confirmation;
    private Button continueButton;
    private Button cancelButton;
    private static Stage globalStage;

    public AskToConfirmClearWindow() {
        openClearConfirmationWindow();
    }

    private void openClearConfirmationWindow() {
        globalStage = new Stage();
        AnchorPane clearConfirmationAnchorPane = new AnchorPane();
        clearConfirmationAnchorPane.prefHeight(100);
        clearConfirmationAnchorPane.prefWidth(230);
        clearConfirmationAnchorPane.setId("clearConfirmationAnchorPane");

        initConfirmation();
        initContinueButton();
        initCancelButton();

        clearConfirmationAnchorPane.getChildren().add(confirmation);
        clearConfirmationAnchorPane.setStyle("-fx-background-color: #3D3D3D");
        clearConfirmationAnchorPane.getChildren().add(continueButton);
        clearConfirmationAnchorPane.getChildren().add(cancelButton);
        clearConfirmationAnchorPane.setVisible(true);
        Scene globalScene = new Scene(clearConfirmationAnchorPane, 230, 100);
        globalStage.setTitle("Confirmation");
        globalStage.setScene(globalScene);
        globalStage.show();
    }

    /**
     * Initialise the button "continue"
     */
    @FXML
    private void initContinueButton(){
        continueButton = new Button("continue");
        continueButton.setLayoutX(20);
        continueButton.setLayoutY(60);
        continueButton.setPrefWidth(70);
        continueButton.setId("continueButton");
        continueButton.setTextFill(Paint.valueOf("white"));
        continueButton.setStyle("-fx-background-color: #E50914;");
        continueButton.setOnAction(event -> continueButtonClicked(globalStage));
    }

    /**
     * Initialise the button "cancel"
     */
    @FXML
    private void initCancelButton(){
        cancelButton = new Button("cancel");
        cancelButton.setLayoutX(140);
        cancelButton.setLayoutY(60);
        cancelButton.setPrefWidth(70);
        cancelButton.setId("cancelButton");
        cancelButton.setTextFill(Paint.valueOf("white"));
        cancelButton.setStyle("-fx-background-color: #E50914;");
        cancelButton.setOnAction(event -> closeClearConfirmationWindow(globalStage));
    }

    /**
     * Initialise the confirmation label
     */
    @FXML
    private void initConfirmation(){
        confirmation = new Label("Do you want to continue?");
        confirmation.setLayoutX(20);
        confirmation.setLayoutY(15);
        confirmation.setTextFill(Paint.valueOf("white"));
        confirmation.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 17px;");
    }
}
