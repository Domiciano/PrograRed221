package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import com.google.gson.Gson;
import model.Answer;
import model.ConnectionRefused;
import model.CorrectAnswer;
import model.Generic;
import model.InitializeFirstQuestion;
import model.Loser;
import model.Message;
import model.User;
import model.Winner;
import model.WrongAnswer;
import model.MathBank;


public class TCPConnectionServer extends Thread implements Receptor.OnMessageListener{

	//SINGLETON
	private static TCPConnectionServer instance = null;
	private TCPConnectionServer() {
		sessions = new ArrayList<>();
		questions1 = new ArrayList<>();
		questions2 = new ArrayList<>();
		setOperationsBank();
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
	private ArrayList<MathBank> questions1;
	private ArrayList<MathBank> questions2;



	@Override
	public void run() {
		try {
			server = new ServerSocket(DISPATCHER_PORT);
			while(true) {
				System.out.println("Esperando en el puerto " + DISPATCHER_PORT);		
				if(sessions.size() < 2) {
					Socket socket = server.accept();
					System.out.println("Nuevo cliente conectado");
					Session session = new Session(socket);
					User user = new User(UUID.randomUUID().toString());
					session.setUser(user);
					sessions.add(session);
					if(sessions.size() == 2) {
						setOponents(session);
					}
				} else {
					Socket socket = server.accept();
					System.out.println("Conexion rechazada por el server");
					Session session = new Session(socket);
					Gson gsonRef = new Gson();
					ConnectionRefused refused = new ConnectionRefused();
					String jsonRef = gsonRef.toJson(refused);
					session.getEmisor().sendMessage(jsonRef);	
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Se desconectó");
		}
	}

	@Override
	public void onMessage(Session session, String msg) {
		Gson gson = new Gson();
		Generic obj = gson.fromJson(msg, Generic.class);

		if(obj != null) {
			switch (obj.getType()) {
			case "ConnectionRefused":
				try {
					session.getReceptor().closeReceptor();
					session.getSocket().close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
				break;

			case "Answer":
				Answer ans = gson.fromJson(msg, Answer.class);
				int questionID = Integer.parseInt(ans.getId());

				for (int i = 0; i < session.getPreguntas().size(); i++) {
					if(session.getPreguntas().get(i).getQuestionNum() == questionID && !session.getPreguntas().get(i).isResuelta()){
						if(Double.parseDouble(session.getPreguntas().get(i).getRespuesta()) == Double.parseDouble(ans.getAnswer())) {
							session.getPreguntas().get(i).setResuelta(true);
							if(session.getPreguntas().get(i).getQuestionNum() == 5) {
								Gson gsonWins = new Gson();
								Winner iWin = new Winner();
								String jsonWin = gsonWins.toJson(iWin);
								session.getEmisor().sendMessage(jsonWin);

								Gson gsonLoser = new Gson();
								Loser loser = new Loser();
								String jsonLoser = gsonLoser.toJson(loser);
								for (int j = 0; j < sessions.size(); j++) {
									if(sessions.get(j).getUser().getOpponent().equals(session.getUser())) {
										sessions.get(j).getEmisor().sendMessage(jsonLoser);
										break;
									}
								}
								break;

							} else {
								int next = 1;
								next += i;
								int nextQuestionID = session.getPreguntas().get(next).getQuestionNum();
								double nextQnum1 = session.getPreguntas().get(next).getNum1();
								double nextQnum2 = session.getPreguntas().get(next).getNum2();
								char nextQoperator = session.getPreguntas().get(next).getOperator();
								Gson gsonCorrect = new Gson();
								CorrectAnswer corrAns = new CorrectAnswer(nextQnum1, nextQnum2, nextQoperator, nextQuestionID);
								String jsonCorrAns = gsonCorrect.toJson(corrAns);
								session.getEmisor().sendMessage(jsonCorrAns);
								Gson gsonMess = new Gson();
								Message msgAdver = new Message(nextQuestionID+"");
								String jsonMsgAdver = gsonMess.toJson(msgAdver);
								for (int j = 0; j < sessions.size(); j++) {
									if(sessions.get(j).getUser().getOpponent().equals(session.getUser())) {
										sessions.get(j).getEmisor().sendMessage(jsonMsgAdver);
										break;
									}

								} 
							}


						} else {
							Gson gsonWrong= new Gson();
							WrongAnswer wrongAns = new WrongAnswer();
							String jsonWrongans = gsonWrong.toJson(wrongAns);
							session.getEmisor().sendMessage(jsonWrongans);
							break;
						}
					}
				}
				break;

			default:
				break;

			}
		} else {
			try {
				session.getReceptor().closeReceptor();
				session.getSocket().close();	
				sessions.remove(session);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void setOponents(Session session) {

		sessions.get(0).getUser().setOpponent(sessions.get(1).getUser());
		sessions.get(0).getPreguntas().addAll(questions1);

		//Se setea el otro oponente
		sessions.get(1).getUser().setOpponent(sessions.get(0).getUser());	
		sessions.get(1).getPreguntas().addAll(questions2);

		initializeFirstQ();
	}

	public void setOperationsBank() {

		for (int i = 0, id = 1; i < 5; i++, id++) {
			double num1 = (int) Math.floor(Math.random()*(99-1+1)+1);
			double num2 = (int) Math.floor(Math.random()*(99-1+1)+1);
			char operadores[] = {'+','-','*','/'}; 
			int op = (int) (Math.random()*4);
			char operacion = operadores[op];
			double resultado = 0;
			switch (operacion) {
			case '+':
				resultado = num1 + num2;
				break;
			case '-':
				resultado = num1 - num2;
				break;
			case '*':
				resultado = num1 * num2;
				break;
			case '/':
				resultado = (Math.round((num1/num2)*100))/100d;
				break;
			default:
				break;
			}	
			MathBank pm1 = new MathBank(id, num1, num2, operacion, resultado+"");
			MathBank pm2 = new MathBank(id, num1, num2, operacion, resultado+"");
			questions1.add(pm1);
			questions2.add(pm2);
		}
	}

	public void initializeFirstQ() {
		Gson gsonInitFirst = new Gson();
		double firstNum1 = questions1.get(0).getNum1();
		double firstNum2 = questions1.get(0).getNum2();
		char firstOperator = questions1.get(0).getOperator();
		int firstQuestionID = questions1.get(0).getQuestionNum();
		InitializeFirstQuestion InitFirstAns = new InitializeFirstQuestion(firstNum1, firstNum2, firstOperator, firstQuestionID);
		String jsonInitFirst = gsonInitFirst.toJson(InitFirstAns);
		sendBroadcast(jsonInitFirst);
	}


	public void sendBroadcast(String msg) {
		for(Session session : sessions) {
			session.getEmisor().sendMessage(msg);
		}
	}

}
