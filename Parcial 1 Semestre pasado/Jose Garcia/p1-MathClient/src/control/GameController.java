package control;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import comm.TCPConnection;
import model.Answer;
import model.Correct;
import model.Defeat;
import model.Generic;
import model.Match;
import view.GameWindow;

public class GameController implements OnMessageListener {

	private GameWindow view;
	private TCPConnection connection;
	private Match currentMatch;
	private int currentQuestion;
	private int advCurrentQuestion;

	public GameController(GameWindow view) {
		this.view = view;
		currentQuestion = 0;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);

		view.getAnswerBtn().setOnAction(event -> {
			Gson gson = new Gson();
			Answer a = new Answer(Double.parseDouble(view.getAnswerTF().getText()), currentQuestion);
			view.getAnswerTF().clear();
			String json = gson.toJson(a);
			TCPConnection.getInstance().getEmisor().sendMessage(json);

		});

	}

	@Override
	public void onMessage(String msg) {
		Gson gson = new Gson();
		Generic obj = gson.fromJson(msg, Generic.class);
		switch (obj.getType()) {
		case "Match":
			Match m = gson.fromJson(msg, Match.class);
			currentMatch = m;
			view.startMatch(m.getQuestions()[0]);
			break;
		case "Correct":
			Correct c = gson.fromJson(msg, Correct.class);
			TCPConnection.getInstance().getEmisor().sendMessage(msg);
			if (c.isWin()) {
				view.win();
			} else {
				currentQuestion++;
				view.nextQuestion(currentMatch.getQuestions()[currentQuestion],currentQuestion);
			}
			break;
		case "Defeat":
			Defeat d = gson.fromJson(msg, Defeat.class);
			if(d.isDefeat()) {
				connection.disconnect();
				view.defeat();
			}
			else {
				advCurrentQuestion++;
				view.advNextQuestion(advCurrentQuestion);
			}
			break;
		}
		/*
		 * Platform.runLater( // run on UI Thread () -> { Gson gson = new Gson();
		 * Message msjObj = gson.fromJson(msg, Message.class);
		 * System.out.println(msjObj.getBody()); }
		 * 
		 * );
		 */
	}

}
