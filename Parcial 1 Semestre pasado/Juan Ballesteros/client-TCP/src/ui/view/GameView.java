package ui.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.controller.GameController;

public class GameView extends Stage {

    // Elements
    private GameController controller;

    private Label problemLabel;
    private Label ownStatus;
    private Label oppStatus;

    private Button validateBtn;

    private TextField answerTF;

    private Scene scene;

    // Constructor
    public GameView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../assets/gameWindow.fxml"));
            Parent parent = loader.load();

            problemLabel = (Label) loader.getNamespace().get("problemLabel");
            ownStatus = (Label) loader.getNamespace().get("ownStatus");
            oppStatus = (Label) loader.getNamespace().get("oppStatus");

            validateBtn = (Button) loader.getNamespace().get("validateBtn");

            answerTF = (TextField) loader.getNamespace().get("answerTF");

            scene = new Scene(parent);
            this.setScene(scene);
            controller = new GameController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Label getProblemLabel() {
        return problemLabel;
    }

    public Label getOwnStatus() {
        return ownStatus;
    }

    public Label getOppStatus() {
        return oppStatus;
    }

    public Button getValidateBtn() {
        return validateBtn;
    }

    public TextField getAnswerTF() {
        return answerTF;
    }

    public void setLabelText(Label label, String text) {
        Platform.runLater(() -> {
            label.setText(text);
        });
    }

    public void buttonDisable(Button btn, boolean isOff){
        btn.setDisable(isOff);
    }

    public void cleanAnswer() {
        Platform.runLater(() -> {
            answerTF.setText("");
        });
    }

}
