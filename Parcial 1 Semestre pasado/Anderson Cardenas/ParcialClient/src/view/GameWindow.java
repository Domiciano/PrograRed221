package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameWindow extends Stage {

	private Scene scene;
	private TextField answerTF;
	private Button submitBtn;
	private Label opponentStatusL;
	private Label statusL;
	private Label questionL;
	private Label statusAnswerL;

	public GameWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 490, 300);
			this.setScene(scene);

			answerTF = (TextField) loader.getNamespace().get("answerTF");
			submitBtn = (Button) loader.getNamespace().get("submitBtn");
			opponentStatusL = (Label) loader.getNamespace().get("opponentStatusL");
			statusL = (Label) loader.getNamespace().get("statusL");
			questionL = (Label) loader.getNamespace().get("questionL");
			statusAnswerL = (Label) loader.getNamespace().get("statusAnswerL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TextField getAnswerTF() {
		return answerTF;
	}

	public Button getSubmitBtn() {
		return submitBtn;
	}

	public Label getOpponentStatusL() {
		return opponentStatusL;
	}

	public Label getStatusL() {
		return statusL;
	}

	public Label getQuestionL() {
		return questionL;
	}

	public Label getStatusAnswerL() {
		return statusAnswerL;
	}

	public void setAnswerTF(TextField answerTF) {
		this.answerTF = answerTF;
	}

	public void setOpponentStatusL(Label opponentStatusL) {
		this.opponentStatusL = opponentStatusL;
	}

	public void setStatusAnswerL(Label statusAnswerL) {
		this.statusAnswerL = statusAnswerL;
	}
	

	
}
