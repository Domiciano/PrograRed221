package control;

import java.io.IOException;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Answer;
import model.Generic;
import model.NextQ;
import model.Question;
import view.GameWindow;

public class GameController implements OnMessageListener {

	private GameWindow view;
	private TCPConnection connection;
	private Question currentQ;

	public GameController(GameWindow view) {
		this.view = view;
		init();
	}

	private void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);
		view.getSubmitBtn().setDisable(true);
		view.getQuestionL().setText("...");
		view.getOpponentStatusL().setText("...");

		view.getSubmitBtn().setOnAction(e -> {
			if (view.getAnswerTF().getText().isEmpty()) {
				Gson gsonA = new Gson();
				Answer ans = new Answer(Double.parseDouble(view.getStatusL().getText()), currentQ.getI());
				String json = gsonA.toJson(ans);
				connection.getEmisor().sendMessage(json);
				view.getAnswerTF().setText("");
			} else {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText(" respuesta incorrecta");
				alert.show();
			}
		});
		view.setOnCloseRequest(e -> {
			connection.getEmisor().sendMessage(null);
		});
	}

	@Override
	public void onMessage(String msg) {

		Platform.runLater(() -> {
			Gson gson = new Gson();
			Generic obj = gson.fromJson(msg, Generic.class);

			switch (obj.getType()) {
			case "Win":
				view.getStatusAnswerL().setText("\\GANADOR//");
				view.getSubmitBtn().setDisable(true);
				break;

			case "Loser":
				view.getStatusAnswerL().setText("\\PERDEDOR//");
				view.getSubmitBtn().setDisable(true);
				break;

			case "FailAnswer":
				Alert alerta = new Alert(Alert.AlertType.WARNING);
				alerta.setContentText("fallaste en la respuesta");
				alerta.show();
				break;

			case "CorrectAnswer":
				currentQ = gson.fromJson(msg, Question.class);
				view.getQuestionL().setText(currentQ.getQuestion());
				view.getStatusAnswerL().setText(currentQ.getI() + 1 + "/5");
				break;

			case "Finish":
				view.close();
				try {
					TCPConnection.getInstance().disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			case "NextQ":
				NextQ newQ = gson.fromJson(msg, NextQ.class);
				view.getOpponentStatusL().setText(newQ.getIndex()+1 + "/5");
				break;
				
			case "Question":
				view.getAnswerTF().setText("");
				currentQ = gson.fromJson(msg, Question.class);
				view.getQuestionL().setText(currentQ.getQuestion());
				view.getStatusL().setText(currentQ.getI()+1 +"/5");
				break;
			}

		});
	}

}
