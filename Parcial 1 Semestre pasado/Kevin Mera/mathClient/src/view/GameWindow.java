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

public class GameWindow extends Stage{


	//UI Elements
	private Scene scene;
	@SuppressWarnings("unused")
	private GameController control;
	private Label num1Label;
	private Label operatLabel;
	private Label num2Label;
	private TextField answerTF;
	private Button answerBtn;
	private Label actualQstnLabel;	
	private Label adversarioQstnLabel;
	private Label statusLabel;


	public GameWindow(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 620, 500);
			this.setScene(scene);

			num1Label = (Label) loader.getNamespace().get("num1Label");
			operatLabel = (Label) loader.getNamespace().get("operatLabel");
			num2Label = (Label) loader.getNamespace().get("num2Label");
			answerTF = (TextField) loader.getNamespace().get("answerTF");
			answerBtn = (Button) loader.getNamespace().get("answerBtn");
			actualQstnLabel = (Label) loader.getNamespace().get("actualQstnLabel");
			adversarioQstnLabel = (Label) loader.getNamespace().get("adversarioQstnLabel");
			statusLabel = (Label) loader.getNamespace().get("statusLabel");
			control = new GameController(this);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Label getNum1Label() {
		return num1Label;
	}

	public Label getOperatLabel() {
		return operatLabel;
	}

	public Label getNum2Label() {
		return num2Label;
	}

	public Label getActualQstnLabel() {
		return actualQstnLabel;
	}

	public Label getAdversarioQstnLabel() {
		return adversarioQstnLabel;
	}

	public TextField getAnswerTF() {
		return answerTF;
	}

	public Button getAnswerBtn() {
		return answerBtn;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}	
	
}
