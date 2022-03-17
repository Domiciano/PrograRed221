package view;

import java.io.IOException;

import control.GameController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class GameWindow extends Stage {

	// UI Elements
	private Scene scene;
	private GameController control;
	private Label operationLb;
	private Label ownQuestionLb;
	private Label adversaryQuestionLb;
	private TextField answerTF;
	private Button answerBtn;

	public GameWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 600, 400);
			this.setScene(scene);
			// Referencias

			operationLb = (Label) loader.getNamespace().get("operationLb");
			ownQuestionLb = (Label) loader.getNamespace().get("ownQuestionLb");
			adversaryQuestionLb = (Label) loader.getNamespace().get("adversaryQuestionLb");
			answerTF = (TextField) loader.getNamespace().get("answerTF");
			answerBtn = (Button) loader.getNamespace().get("answerBtn");

			control = new GameController(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Label getOperationLb() {
		return operationLb;
	}

	public Label getOwnQuestionLb() {
		return ownQuestionLb;
	}

	public Label getAdversaryQuestionLb() {
		return adversaryQuestionLb;
	}

	public TextField getAnswerTF() {
		return answerTF;
	}

	public Button getAnswerBtn() {
		return answerBtn;
	}

	public void defeat() {

		Platform.runLater(

				() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Defeat");
					alert.setHeaderText("You were defeated");
					alert.setContentText("Opponent finished first!");

					alert.showAndWait();
					this.close();
					System.exit(0);
				}

		);
	}

	public void win() {
		Platform.runLater(

				() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Victory");
					alert.setHeaderText("You were victorious");
					alert.setContentText("You answered correctly all questions first or your opponent gave up!");
					alert.showAndWait();
					this.close();
					System.exit(0);
				}

		);
	}


	public void startMatch(String q1) {
		Platform.runLater(

				() -> {
					adversaryQuestionLb.setText("1/5");
					ownQuestionLb.setText("1/5");
					operationLb.setText(q1);
				}

		);
	}

	public void nextQuestion(String string, int currentQuestion) {
		Platform.runLater(

				() -> {
					operationLb.setText(string);
					ownQuestionLb.setText((currentQuestion+1)+"/5");
				}
		);

	}

	public void advNextQuestion(int advCurrentQuestion) {
		Platform.runLater(

				() -> {
					adversaryQuestionLb.setText((advCurrentQuestion+1)+"/5");
				}
		);
	}

}
