package control;

import comm.Receptor.OnMessageListener;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import model.Advance;
import model.Answer;
import model.Defeat;
import model.Disconnect;
import model.Generic;
import model.Question;
import model.Rejected;
import model.Victory;
import model.User;
import view.FinalWindow;
import view.GameWindow;
import view.RegisterWindow;

public class GameController implements OnMessageListener {

	private GameWindow view;
	private RegisterWindow firstView;
	private FinalWindow finalView;
	private TCPConnection connection;
	private Question current;

	public GameController(RegisterWindow view) {
		this.firstView = view;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);
		Gson gson = new Gson();

		firstView.getSendNameBtn().setOnAction(event -> {
			if (firstView.getNameTF().getText().equals("")) {
				firstView.getWarningLabel().setText("You need an username!");
			}
			else {
				User user = new User(UUID.randomUUID().toString(), firstView.getNameTF().getText());
				String json = gson.toJson(user);
				TCPConnection.getInstance().getEmisor().sendMessage(json);
			}
		});
	}

	public void getReady(Gson gson) {
		view.getSubmitBtn().setOnAction(event ->{
			Answer answer = new Answer(Double.parseDouble(view.getAnswerTF().getText()), current.getIndex());
			String msg = gson.toJson(answer);
			TCPConnection.getInstance().getEmisor().sendMessage(msg);
		});
		view.setOnCloseRequest(event ->{
			Disconnect dis = new Disconnect("Window closed");
			String gg = gson.toJson(dis);
			TCPConnection.getInstance().getEmisor().sendMessage(gg);
		});
	}

	@Override
	public void onMessage(String msg) {
		Platform.runLater(() -> {
			try {
				Gson gson = new Gson();
				Generic obj = gson.fromJson(msg, Generic.class);

				switch (obj.getType()) {
				case "Rejected":
					Rejected rejected = gson.fromJson(msg, Rejected.class);
					if(rejected.getWhy().equalsIgnoreCase("Match running!")) {
						firstView.getWarningLabel().setText(rejected.getWhy());
						Disconnect dis = new Disconnect("Match running");
						String gg = gson.toJson(dis);
						TCPConnection.getInstance().getEmisor().sendMessage(gg);
					}else if(rejected.getWhy().equalsIgnoreCase("That name already exists!")) {
						firstView.getWarningLabel().setText(rejected.getWhy());
					}else if(rejected.getWhy().equalsIgnoreCase("Incorrect!")) {
						view.getFailLabel().setText(rejected.getWhy());
						view.getAnswerTF().setText("");
					}
					break;
				case "Accepted":
					firstView.getSendNameBtn().setDisable(true);
					firstView.getNameTF().setDisable(true);
					firstView.getWarningLabel().setText("Waiting for opponent");
					break;
				case "Start":
					firstView.close();
					view = new GameWindow();
					view.show();
					getReady(gson);
					break;
				case "Question":
					view.getFailLabel().setText("");
					view.getAnswerTF().setText("");
					current = gson.fromJson(msg, Question.class);
					view.getQuestionLabel().setText(current.getQuestion());
					view.getStatusLabel().setText(current.getIndex()+1 + "/5");
					if (current.getQuestion().contains("/")) {
						view.getInfoLabel().setVisible(true);
					}else {
						view.getInfoLabel().setVisible(false);
					}
					break;
				case "Advance":
					Advance advance = gson.fromJson(msg, Advance.class);
					view.getOpponentStatusLabel().setText(advance.getIndex()+1 + "/5");
					break;
				case "Victory":
					Victory victory = gson.fromJson(msg, Victory.class);
					finalView = new FinalWindow();
					finalView.getResultLabel().setText("Win!");
					finalView.getEnemyUsernameLabel().setText("You beat " + victory.getBeated());
					view.close();
					finalView.show();
					Disconnect dis = new Disconnect("Match finished");
					String gg = gson.toJson(dis);
					TCPConnection.getInstance().getEmisor().sendMessage(gg);
					break;
				case "Defeat":
					Defeat defeat = gson.fromJson(msg, Defeat.class);
					finalView = new FinalWindow();
					finalView.getResultLabel().setText("Defeat");
					finalView.getEnemyUsernameLabel().setText("You were beated by " + defeat.getWinner());
					view.close();
					finalView.show();
					Disconnect disc = new Disconnect("Match finished");
					String ggs = gson.toJson(disc);
					TCPConnection.getInstance().getEmisor().sendMessage(ggs);
					break;
				case "Disconnect":
					firstView.close();
					TCPConnection.getInstance().disconnect();
					break;
				}
			}catch(IOException e) {
				System.out.println("Cliente desconectado");
			}
		});
	}

}