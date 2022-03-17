package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GameWindow extends Stage{
	
	//UI Elements
	private Scene scene;
	private TextField answerTF;
	private Button submitBtn;
	private Label opponentStatusLabel;
	private Label statusLabel;	
	private Label questionLabel;
	private Label failLabel;
	private Label infoLabel;
	
	public GameWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 487,290);
			this.setScene(scene);
			
			//Referencias
			answerTF = (TextField) loader.getNamespace().get("answerTF");
			opponentStatusLabel = (Label) loader.getNamespace().get("opponentStatusLabel");
			statusLabel = (Label) loader.getNamespace().get("statusLabel");
			questionLabel = (Label) loader.getNamespace().get("questionLabel");
			failLabel = (Label) loader.getNamespace().get("failLabel");
			infoLabel = (Label) loader.getNamespace().get("infoLabel");
			submitBtn = (Button) loader.getNamespace().get("submitBtn");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TextField getAnswerTF() {
		return answerTF;
	}

	public Label getOpponentStatusLabel() {
		return opponentStatusLabel;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}

	public Label getQuestionLabel() {
		return questionLabel;
	}

	public Label getFailLabel() {
		return failLabel;
	}

	public Label getInfoLabel() {
		return infoLabel;
	}

	public Button getSubmitBtn() {
		return submitBtn;
	}

}