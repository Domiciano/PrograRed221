package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.Gson;

import exception.TimeException;
import model.*;

public class TPCServer extends Thread implements Receptor.OnMessageListener {

	// SINGLETON
	private static TPCServer instance = null;
	public static final int DISPATCHER_PORT = 5000;

	private TPCServer() {
		sessions = new ArrayList<>();
		gson = new Gson();
		sessionQueue = new LinkedList<>();
		game = new Game();

	}

	public static synchronized TPCServer getInstance() {
		if (instance == null) {
			instance = new TPCServer();
		}
		return instance;
	}

	// GLOBAL
	private ServerSocket server;
	private ArrayList<Session> sessions;
	private Queue<Session> sessionQueue;
	private Gson gson;
	private Game game;

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

	public void sendBroadcast(String msg) {
		for (Session session : sessions) {
			session.getEmisor().sendMessage(msg);
		}
	}

	public void sendDirectTwo(Session sessionTo, Session sessionFrom, String msg) {

		for (Session session : sessions) {
			if (session == sessionTo || session == sessionFrom) {
				session.getEmisor().sendMessage(msg);
			}
		}
	}

	public void sendDirectOne(Session sessionTo, String msg) {

		for (Session session : sessions) {
			if (session.equals(sessionTo)) {
				session.getEmisor().sendMessage(msg);
			}
		}
	}

	// Server Actions
	@Override
	public void onMessage(Session session, String msg) {

		Generic obj = gson.fromJson(msg, Generic.class);

		switch (obj.getType()) {
			case "User":
				User user = gson.fromJson(msg, User.class);
				// System.out.println(user.getUsername());
				session.setUser(user);
				sessionQueue.add(session);
				matchPlayers();
				break;

			case "Game":
				Game gameIn = gson.fromJson(msg, Game.class);
				if (gameIn.isFull()) {
					Session one = findSession(gameIn.getUsers()[0]);
					Session two = findSession(gameIn.getUsers()[1]);
					if (gameIn.getUsers()[0].isFinish() && gameIn.getUsers()[1].isFinish()) {
						try {
							if (gameIn.getUsers()[0].getTime().getTime() < gameIn.getUsers()[1].getTime().getTime()) {
								gameIn.setWinner(gameIn.getUsers()[0]);
							} else if (gameIn.getUsers()[0].getTime().getTime() > gameIn.getUsers()[1].getTime()
									.getTime()) {
								gameIn.setWinner(gameIn.getUsers()[1]);
							} else {
								User winUser = ((int) Math.random() * 1 == 0) ? gameIn.getUsers()[0]
										: gameIn.getUsers()[1];
								gameIn.setWinner(winUser);
							}
							
							msg = gson.toJson(gameIn);
							game = new Game();

						} catch (TimeException e) {
							e.printStackTrace();
						}
					}
					sendDirectTwo(one, two, msg);
				} else {
					Session one = findSession(gameIn.getUsers()[0]);
					sendDirectOne(one, msg);
				}
				break;

			default:
				break;
		}
	}

	private void matchPlayers() {
		while (!sessionQueue.isEmpty()) {
			if (game.getUsers()[0] == null) {
				game.getUsers()[0] = sessionQueue.peek().getUser();
				sessionQueue.peek().getUser().setGame(game);

				String msg = gson.toJson(game);
				sendDirectOne(sessionQueue.poll(), msg);

			} else if (game.getUsers()[1] == null) {
				game.getUsers()[1] = sessionQueue.peek().getUser();
				sessionQueue.peek().getUser().setGame(game);

				String msg = gson.toJson(game);
				sendDirectTwo(findSession(game.getUsers()[0]), sessionQueue.poll(), msg);
			} else { // Reject user
				String msg = gson.toJson(new Reject());
				sendDirectOne(sessionQueue.poll(), msg);
			}
			/*
			 * if (game.isFull()) { game = new Game(); }
			 */
		}
	}

	public Session findSession(User user) {
		boolean validation = false;
		Session temp = null;

		for (int i = 0; i < sessions.size() && !validation; i++) {
			if (sessions.get(i).getUser().getId().equals(user.getId())) {
				temp = sessions.get(i);
				validation = true;
			}
		}

		return temp;
	}

}
