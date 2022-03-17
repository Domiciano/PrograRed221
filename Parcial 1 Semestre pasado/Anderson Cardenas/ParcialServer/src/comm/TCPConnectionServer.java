package comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.NextQ;
import model.Answer;
import model.Finish;
import model.Generic;
import model.Loser;
import model.Question;
import model.Questionnaire;
import model.Reject;
import model.User;
import model.Win;

public class TCPConnectionServer extends Thread implements Receptor.OnMessageListener {

	// SINGLETON
	private static TCPConnectionServer instance = null;

	private TCPConnectionServer() {
		sessions = new ArrayList<>();
	}

	public static synchronized TCPConnectionServer getInstance() {
		if (instance == null) {
			instance = new TCPConnectionServer();
		}
		return instance;
	}

	public static final int DISPATCHER_PORT = 5000;

	// GLOBAL
	private ServerSocket server;
	private ArrayList<Session> sessions;
	private ArrayList<Question> questions;
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(DISPATCHER_PORT);

			while (true) {
				System.out.println("Esperando en el puerto " + DISPATCHER_PORT);
				Socket socket = server.accept();
				System.out.println("Nuevo cliente conectado");

				Session session = new Session(socket);
				sessions.add(session);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void newQuestionnaire() {
		Questionnaire questionnaire = new Questionnaire();
		for (int i = 0; i < 5; i++) {
			Question question = new Question(questionnaire.randomQuestion(), questionnaire.result, i);
			questions.add(question);
		}
	}
	
	public void startGame() {
		if (sessions.size() >= 2) {
			if (sessions.get(0).getUser() != null && sessions.get(1).getUser() != null) {
				Gson gson = new Gson();
				String start = "start";
				String msg = gson.toJson(start);
				sessions.get(0).getEmisor().sendMessage(msg);
				sessions.get(1).getEmisor().sendMessage(msg);
				questions.clear();
				newQuestionnaire();
				Question question = questions.get(0);
				String first = gson.toJson(question);
				sessions.get(0).getEmisor().sendMessage(first);
				sessions.get(1).getEmisor().sendMessage(first);
				sessions.get(0).setOpponent(sessions.get(1));
				sessions.get(1).setOpponent(sessions.get(0));
			}
		}
	}

	@Override
	public void onMessage(Session session, String msg) {
		Gson gson = new Gson();
		Generic obj = gson.fromJson(msg, Generic.class);

		switch (obj.getType()) {
		case "User":
			boolean exist = false;
			User user = gson.fromJson(msg, User.class);
			for(int i = 0;i<sessions.size();i++) {
				if(sessions.get(i).getUser()!=null) {
					if(sessions.get(i).getUser().getName().equalsIgnoreCase(user.getName())) {
						exist = true;
					}
				}
			}if(exist) {
				
			}
			break;
			
		case "Answer":
			Answer answer = gson.fromJson(msg, Answer.class);
			if(questions.get(answer.getI()).getResult()==answer.getAnswer()) {
				if(answer.getI()<4) {
					Question question = questions.get(answer.getI()+1);
					String json = gson.toJson(question);
					session.getEmisor().sendMessage(json);
					NextQ advance = new NextQ(answer.getI()+1);
					String json2 = gson.toJson(advance);
					session.getOpponent().getEmisor().sendMessage(json2);
				}else {
					Loser loser = new Loser(session.getUser().getName());
					String json = gson.toJson(loser);
					session.getOpponent().getEmisor().sendMessage(json);
					Win win = new Win(session.getOpponent().getUser().getName());
					String json2 = gson.toJson(win);
					session.getEmisor().sendMessage(json2);
				}
			}else {
				Reject reject = new Reject("fail");
				String json = gson.toJson(reject);
				session.getEmisor().sendMessage(json);
			}
			break;
			
		case "Finish":
			Finish finishGame = gson.fromJson(msg, Finish.class);
			if(finishGame.getFinish().equalsIgnoreCase("juego terminado")) {
				sessions.remove(session);
				sessions.remove(session.getOpponent());
				session.getEmisor().sendMessage(msg);
			}else {
				
			}
			break;
		}
	}

}
