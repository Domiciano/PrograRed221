package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Answer;
import model.Correct;
import model.Defeat;
import model.Generic;
import model.User;

public class TCPConnectionServer extends Thread implements Receptor.OnMessageListener {

	// SINGLETON
	private static TCPConnectionServer instance = null;

	private TCPConnectionServer() {
		sessions = new ArrayList<>();
		answers= new double[5];
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
	private double[] answers;

	@Override
	public void run() {
		try {
			server = new ServerSocket(DISPATCHER_PORT);

			while (true) {
				if(sessions.size()<2 && server.isClosed())
					server = new ServerSocket(DISPATCHER_PORT);
				if (sessions.size() ==0) {
					System.out.println("Esperando user1 en el puerto " + DISPATCHER_PORT);
					Socket socket = server.accept();
					System.out.println("User1 conectado");
					Session session = new Session(socket);
					sessions.add(session);
				}
				else if (sessions.size() == 1) {
					System.out.println("Esperando user2 en el puerto " + DISPATCHER_PORT);
					Socket socket = server.accept();
					System.out.println("user2 conectado.");
					Session session = new Session(socket);
					sessions.add(session);
					//checkMatch();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void checkMatch() {
		if (sessions.size() == 2) {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Session firstAlone = sessions.get(0);
			Session secondAlone = sessions.get(1);
			firstAlone.setOpponent(secondAlone);
			secondAlone.setOpponent(firstAlone);
			String[] questions = createQuestions();
			firstAlone.startMatch(questions);
			secondAlone.startMatch(questions);
		}
	}

	private String[] createQuestions() {
		String[] questions = new String[5];
		String[] operators = {"+","-","*","/","/"};	
		for (int i = 0; i < questions.length; i++) {
			int number1 = (int) (100 * Math.random());
			int number2 = (int) (100 * Math.random())+1;
			questions[i]=number1+operators[i]+number2;
			System.out.println("Question "+i+": "+questions[i]);
			switch(i) {
			case 0:
				answers[i]=(double)((double)number1+(double)number2);
				break;
			case 1:
				answers[i]=(double)((double)number1-(double)number2);
				break;
			case 2:
				answers[i]=(double)((double)number1*(double)number2);
				break;
			case 3:
				answers[i]=(double)((double)number1/(double)number2);
				break;
			case 4:
				answers[i]=(double)((double)number1/(double)number2);
				break;
			}
			System.out.println("Answer "+i+ ": "+answers[i]);
		}
		return questions;
	}

	@Override
	public void onMessage(Session session, String msg) {

		System.out.println(msg);
		Gson gson = new Gson();
		Generic obj = gson.fromJson(msg, Generic.class);

		switch (obj.getType()) {
		case "User":
			User user = gson.fromJson(msg, User.class);
			System.out.println("user connected: "+user.getUsername());
			session.setUser(user);
			checkMatch();
			break;
		case "Correct":
			Correct c = gson.fromJson(msg, Correct.class);
			Defeat d = new Defeat(c.isWin());
			String json = gson.toJson(d);
			session.getOpponent().getEmisor().sendMessage(json);
			break;
		case "Answer":
			Answer a =  gson.fromJson(msg, Answer.class);
			if(isCorrect(a)) {
				session.isCorrect(a);
			}
			break;
		default:
			System.out.println("AAAAAA entró un "+obj.getType());
			break;
		}
	}

	private boolean isCorrect(Answer a) {
		double app=answers[a.getQuestion()];
		app*=100;
		int appr = (int)app;
		double approx=(double)appr;
		approx/=100;
		return approx==a.getAnswer();
	}

	public void removeSession(Session session) {
		session.getOpponent().getEmisor().sendMessage(new Gson().toJson(new Correct(true)));
		sessions.remove(session);
	}

	public int getNumberOfSessions() {
		return sessions.size();
	}


}
