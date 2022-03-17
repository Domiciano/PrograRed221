package ui.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controller.ConnectionController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConnectionView extends Stage {

    // Elements
    private TextField ipTF;
    private TextField portTF;
    private Label warmingLabel;

    private Button closeBtn;
    private Button playBtn;
    private Button localBtn;

    private Scene scene;
    
    private ConnectionController controller;
    
    // Constructor
    public ConnectionView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../assets/connectionWindow.fxml"));
            Parent parent = loader.load();

            ipTF = (TextField) loader.getNamespace().get("ipTF");
            portTF = (TextField) loader.getNamespace().get("portTF");
            warmingLabel = (Label) loader.getNamespace().get("warmingLabel");
            warmingLabel.setVisible(false);

            closeBtn = (Button) loader.getNamespace().get("closeBtn");
            playBtn = (Button) loader.getNamespace().get("playBtn");
            localBtn = (Button) loader.getNamespace().get("localBtn");
            
            scene = new Scene(parent);
            this.setScene(scene);
            controller = new ConnectionController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLabelText(Label label, String text) {
        Platform.runLater(() -> {
            label.setText(text);
        });
    }

    public void warmingLabelVisible(){
        Platform.runLater(()->{
            warmingLabel.setVisible(!warmingLabel.isVisible());
        });
    }

    public TextField getIpTF() {
        return ipTF;
    }

    public TextField getPortTF() {
        return portTF;
    }

    public Label getWarmingLabel() {
        return warmingLabel;
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public Button getPlayBtn() {
        return playBtn;
    }


    public Button getLocalBtn() {
        return localBtn;
    }
    
}
