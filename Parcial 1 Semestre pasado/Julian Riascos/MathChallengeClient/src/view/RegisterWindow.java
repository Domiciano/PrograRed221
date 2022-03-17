package view;

import java.io.IOException;

import control.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterWindow extends Stage{

	private Scene scene;
	@SuppressWarnings("unused")
	private GameController control;
	private TextField nameTF;
	private Button sendNameBtn;
	private Label warningLabel;
	
	public RegisterWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterWindow.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 432,192);
			this.setScene(scene);
			nameTF = (TextField) loader.getNamespace().get("nameTF");
			sendNameBtn = (Button) loader.getNamespace().get("sendNameBtn");
			warningLabel = (Label) loader.getNamespace().get("warningLabel");
			
			control = new GameController(this);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TextField getNameTF() {
		return nameTF;
	}
	
	public Button getSendNameBtn() {
		return sendNameBtn;
	}

	public Label getWarningLabel() {
		return warningLabel;
	}

	public void setWarningLabel(Label warningLabel) {
		this.warningLabel = warningLabel;
	}
}