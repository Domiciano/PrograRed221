package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Accepted;
import model.Advance;
import model.Answer;
import model.Generic;
import model.Question;
import model.Questionnaire;
import model.Rejected;
import model.Defeat;
import model.Disconnect;
import model.Start;
import model.User;
import model.Victory;

public class TCPConnectionServer extends Thread implements Receptor.OnMessageListener {

	// SINGLETON
	private static TCPConnectionServer instance = null;

	private TCPConnectionServer() {
		sessions = new ArrayList<>();
		questions = new ArrayList<>();
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

				if(sessions.size() > 2) {
					Gson gson = new Gson();
					Rejected rejected = new Rejected("Match running!");
					String tryLater = gson.toJson(rejected);
					session.getEmisor().sendMessage(tryLater);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startGame() {
		if (sessions.size() >= 2) {
			if (sessions.get(0).getUser() != null && sessions.get(1).getUser() != null) {
				Gson gson = new Gson();
				Start start = new Start();
				String msg = gson.toJson(start);
				sessions.get(0).getEmisor().sendMessage(msg);
				sessions.get(1).getEmisor().sendMessage(msg);
				questions.clear();
				createQuestionnaire();
				Question question = questions.get(0);
				String first = gson.toJson(question);
				sessions.get(0).getEmisor().sendMessage(first);
				sessions.get(1).getEmisor().sendMessage(first);
				sessions.get(0).setEnemy(sessions.get(1));
				sessions.get(1).setEnemy(sessions.get(0));
			}
		}
	}

	public void createQuestionnaire() {
		Questionnaire questionnaire = new Questionnaire();
		for (int i = 0; i < 5; i++) {
			Question question = new Question(questionnaire.question(), questionnaire.result, i);
			questions.add(question);
		}
	}

	@Override
	public void onMessage(Session session, String msg) {

		Gson gson = new Gson();
		Generic obj = gson.fromJson(msg, Generic.class);

		switch (obj.getType()) {
		case "User":
			boolean exists = false;
			User user = gson.fromJson(msg, User.class);
			for (int i = 0; i < sessions.size(); i++) {
				if (sessions.get(i).getUser() != null) {
					if (sessions.get(i).getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
						exists = true;
					}
				}
			}
			if (exists) {	
				Rejected rejected = new Rejected("That name already exists!");
				String alreadyExists = gson.toJson(rejected);
				session.getEmisor().sendMessage(alreadyExists);
			}
			else {
				Accepted accepted = new Accepted();
				String notExists = gson.toJson(accepted);
				session.getEmisor().sendMessage(notExists);
				session.setUser(user);
				startGame();
			}
			break;
		case "Answer":
			Answer answer = gson.fromJson(msg, Answer.class);
			if (questions.get(answer.getIndex()).getResult() == answer.getAnswer()) {
				if(answer.getIndex() < 4) {
					Question question = questions.get(answer.getIndex()+1);
					String nice = gson.toJson(question);
					session.getEmisor().sendMessage(nice);
					Advance advance = new Advance(answer.getIndex()+1);
					String go = gson.toJson(advance);
					session.getEnemy().getEmisor().sendMessage(go);
				}else {
					Defeat defeat = new Defeat(session.getUser().getUsername());
					String f = gson.toJson(defeat);
					session.getEnemy().getEmisor().sendMessage(f);
					Victory victory = new Victory(session.getEnemy().getUser().getUsername());
					String nice = gson.toJson(victory);
					session.getEmisor().sendMessage(nice);
				}
			}else {
				Rejected rejected = new Rejected("Incorrect!");
				String noNice = gson.toJson(rejected);
				session.getEmisor().sendMessage(noNice);
			}
			break;
		case "Disconnect":
			Disconnect disconnect = gson.fromJson(msg, Disconnect.class);
			if (disconnect.getWhy().equalsIgnoreCase("Match finished")) {
				sessions.remove(session);
				sessions.remove(session.getEnemy());
				session.getEnemy().getEmisor().sendMessage(msg);
				session.getEmisor().sendMessage(msg);
			} else if(disconnect.getWhy().equalsIgnoreCase("Match running")) {
				sessions.remove(session);
				session.getEmisor().sendMessage(msg);
			} else if(disconnect.getWhy().equalsIgnoreCase("Window closed")) {
				sessions.remove(session);
				session.getEmisor().sendMessage(msg);
			}
			break;
		}
	}

}