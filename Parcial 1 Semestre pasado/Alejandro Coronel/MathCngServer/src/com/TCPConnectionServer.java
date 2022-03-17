package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.google.gson.Gson;

import model.Answer;
import model.Disconnect;
import model.Generic;
import model.Question;
import model.Quiz;
import model.Refresh;
import model.Rejected;
import model.Win;
import model.Wrong;

public class TCPConnectionServer extends Thread implements Receptor.OnMessageListener{

	//SINGLETON
	private static TCPConnectionServer instance = null;
	private TCPConnectionServer() {
		sessions = new ArrayList<>();
		quiz = new Quiz();
	}
	public static synchronized TCPConnectionServer getInstance() {
		if(instance == null) {
			instance = new TCPConnectionServer();
		}
		return instance;
	}

	public static final int DISPATCHER_PORT = 5000;

	//GLOBAL
	private ServerSocket server;
	private ArrayList<Session> sessions;
	private Quiz quiz;
	@Override
	public void run() {
		try {
			server = new ServerSocket(DISPATCHER_PORT);
			Gson gson = new Gson();
			while(true) {	

				System.out.println("Esperando en el puerto " + DISPATCHER_PORT);
				Socket socket = server.accept();
				Session session = new Session(socket);
				session.setSocket(socket);
				if(sessions.size()<2) {
					System.out.println("Nuevo cliente conectado");
					sessions.add(session);
					if(sessions.size() == 2) {	
						quiz.initQuestions();				
						Question q = new Question(1,quiz.getQuestions().get(0).getStatement());
						String json = gson.toJson(q);
						session.getEmisor().sendMessage(json);
						sessions.get(0).getEmisor().sendMessage(json);
					}

				}else {					
					Rejected reject = new Rejected();
					String json = gson.toJson(reject);
					session.getEmisor().sendMessage(json);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkAnswer(Answer a) {
		boolean correct = false;
		for(int i = 0; i<quiz.getQuestions().size();i++) {
			if(a.getQid() == quiz.getQuestions().get(i).getId()) {
				if(a.getNumber() == quiz.getQuestions().get(i).getAnswer()) {    
					correct = true;
					break;
				}else {    
					correct = false;
					break;
				}
			}
		}
		return correct;
	}

	@Override
	public void onMessage(Session session, String msg) {
		Gson gson = new Gson();
		Generic obj = gson.fromJson(msg, Generic.class);
		switch(obj.getType()) {		
		case "Disconnect":				
			Disconnect d = gson.fromJson(msg, Disconnect.class);
			try {
				session.getSocket().close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}      
			sessions.remove(session);
			if(d.isByRejection() == false) {
				
				quiz.getQuestions().clear();
			}
			
			break;
		case "Answer":		
			Answer a = gson.fromJson(msg, Answer.class);
			boolean correct = checkAnswer(a);
			if(correct == true) {	
				int newId = a.getQid()+1;
				if(newId > 5) {
					Win w = new Win(true);
					Win notwin = new Win(false);
					String jsonW = gson.toJson(w);
					String jsonNW = gson.toJson(notwin);
					session.getEmisor().sendMessage(jsonW);
					if(session == sessions.get(0)) {
						sessions.get(1).getEmisor().sendMessage(jsonNW);        
					}else {			
						sessions.get(0).getEmisor().sendMessage(jsonNW);
					}								
				}else {
					Question  q = new Question(newId, quiz.getQuestions().get(newId-1).getStatement());
					String jsonA = gson.toJson(q);	
					Refresh r = new Refresh(String.valueOf(newId));
					String jsonR = gson.toJson(r);	
					session.getEmisor().sendMessage(jsonA);
					if(session == sessions.get(0)) {
						sessions.get(1).getEmisor().sendMessage(jsonR);        
					}else {			
						sessions.get(0).getEmisor().sendMessage(jsonR);
					}			
				}

			}else {			
				Wrong w = new Wrong();
				String jsonW = gson.toJson(w);	
				session.getEmisor().sendMessage(jsonW);		
			}

		}
	}

}
