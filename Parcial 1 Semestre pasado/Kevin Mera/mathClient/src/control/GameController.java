package control;

import com.google.gson.Gson;
import comm.TCPConnection;
import comm.Receptor.OnMessageListener;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import model.Answer;
import model.ConnectionRefused;
import model.CorrectAnswer;
import model.Generic;
import model.InitializeFirstQuestion;
import model.Message;
import view.GameWindow;

public class GameController implements OnMessageListener{

	private GameWindow view;
	private TCPConnection connection;

	public GameController(GameWindow view) {
		this.view = view;
		init();


	}

	private void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);
		view.getAnswerBtn().setDisable(true);
		view.getActualQstnLabel().setText("Esperando...");
		view.getAdversarioQstnLabel().setText("Esperando...");

		view.getAnswerBtn().setOnAction(event ->{
			if(!view.getAnswerTF().getText().isEmpty()) {
				Gson gsonAns = new Gson();
				Answer ans = new Answer(view.getActualQstnLabel().getText().substring(0, 1),view.getAnswerTF().getText());
				String jsonAns= gsonAns.toJson(ans);
				connection.getEmisor().sendMessage(jsonAns);
				view.getAnswerTF().setText("");
			} else {
				Alert a = new Alert(Alert.AlertType.WARNING);
				a.setContentText("Introduzca una respuesta válida");
				a.show();
			}		
		});
		
		view.setOnCloseRequest(event->{

			connection.getEmisor().sendMessage(null);

		});

	}



	@Override
	public void onMessage(String msg) {
		Platform.runLater( // run on UI Thread
				() -> {
					Gson gson = new Gson();
					Generic obj = gson.fromJson(msg, Generic.class);
					switch (obj.getType()) {

					case "ConnectionRefused":
						Alert a = new Alert(Alert.AlertType.WARNING);
						a.setContentText("Ya hay dos jugadores conectados, intente más tarde");
						a.show();
						Gson gsonAns = new Gson();
						ConnectionRefused conRef = new ConnectionRefused();
						String jsonRef= gsonAns.toJson(conRef);
						connection.getEmisor().sendMessage(jsonRef);
						view.close();
						break;

					case "WrongAnswer":
						Alert w = new Alert(Alert.AlertType.WARNING);
						w.setContentText("Respuesta equivocada");
						w.show();
						break;

					case "CorrectAnswer":
						CorrectAnswer ans = gson.fromJson(msg, CorrectAnswer.class);
						view.getNum1Label().setText(ans.getNum1()+"");
						view.getNum2Label().setText(ans.getNum2()+"");
						view.getOperatLabel().setText(ans.getOperator()+"");
						view.getActualQstnLabel().setText(ans.getNextQuestionID()+"/5");
						break;

					case "InitializeFirstQuestion":
						InitializeFirstQuestion initQ = gson.fromJson(msg, InitializeFirstQuestion.class);
						view.getNum1Label().setText(initQ.getNum1()+"");
						view.getNum2Label().setText(initQ.getNum2()+"");
						view.getOperatLabel().setText(initQ.getOperator()+"");
						view.getActualQstnLabel().setText(initQ.getQuestionID()+"/5");
						view.getAdversarioQstnLabel().setText(initQ.getQuestionID()+"/5");
						view.getAnswerBtn().setDisable(false);
						break;

					case "Message":
						Message msgFromAdv = gson.fromJson(msg, Message.class);
						view.getAdversarioQstnLabel().setText(msgFromAdv.getBody()+"/5");
						break;

					case "Winner":
						view.getStatusLabel().setTextFill(Color.color(0, 1, 0));
						view.getStatusLabel().setText(">> ¡GANADOR! <<");
						view.getAnswerBtn().setDisable(true);
						break;

					case "Loser":
						view.getStatusLabel().setTextFill(Color.color(1, 0, 0));
						view.getStatusLabel().setText(">> ¡PERDEDOR! <<");
						view.getAnswerBtn().setDisable(true);
						break;
					default:
						break;
					}
				}
				);
	}

}
