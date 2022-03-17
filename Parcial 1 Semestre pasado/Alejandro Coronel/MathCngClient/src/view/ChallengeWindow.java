package view;

import java.io.IOException;

import control.ChallengeController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChallengeWindow  extends Stage{

	//UIElements

	private Scene scene;
	private Label opponentlabel;
	private Label questionlabel;
	private TextField answerTF;
	private Label question;
	private Button answerBtn;
	private ChallengeController control;

	public ChallengeWindow() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("answerBase.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 487,421);
			this.setScene(scene);
			
			//Referencias fxml
			
			opponentlabel = (Label) loader.getNamespace().get("opponentlabel");
			questionlabel = (Label) loader.getNamespace().get("questionlabel");
			question = (Label) loader.getNamespace().get("question");
			question.setAlignment(Pos.CENTER);
			answerTF = (TextField) loader.getNamespace().get("answerTF");
			answerBtn = (Button) loader.getNamespace().get("answerBtn");
			control = new ChallengeController(this);
	

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Label getOpponentlabel() {
		return opponentlabel;
	}

	public void setOpponentlabel(Label opponentlabel) {
		this.opponentlabel = opponentlabel;
	}

	public Label getQuestionlabel() {
		return questionlabel;
	}

	public void setQuestionlabel(Label questionlabel) {
		this.questionlabel = questionlabel;
	}

	public TextField getAnswerTF() {
		return answerTF;
	}

	public Label getQuestion() {
		return question;
	}

	public void setQuestion(Label question) {
		this.question = question;
	}

	public Button getAnswerBtn() {
		return answerBtn;
	}

	public void setAnswerBtn(Button answerBtn) {
		this.answerBtn = answerBtn;
	}

}
