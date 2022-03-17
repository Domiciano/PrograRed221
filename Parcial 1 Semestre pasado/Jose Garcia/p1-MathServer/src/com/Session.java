package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

import model.Answer;
import model.Correct;
import model.Match;
import model.User;

public class Session{
	
	private User user;
	private Receptor receptor;
	private Emisor emisor;
	private Session opponent;
	
	
	public Session(Socket socket) {	
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			receptor = new Receptor(this, reader);
			receptor.setListener(TCPConnectionServer.getInstance());
			receptor.start();
			
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			emisor = new Emisor(writer);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public Emisor getEmisor() {
		return this.emisor;
	}
	
	public Receptor getReceptor() {
		return this.receptor;
	}



	public Session getOpponent() {
		return opponent;
	}

	public void setOpponent(Session oponent) {
		this.opponent = oponent;
	}

	public void startMatch(String[] questions) {
		Gson gson = new Gson();
		Match m = new Match(opponent.getUser(),questions);
		String json = gson.toJson(m);
		emisor.sendMessage(json);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void isCorrect(Answer a) {
		Gson gson = new Gson();
		Correct c;
		if(a.getQuestion()==4) 
			c= new Correct(true);
		else
			c= new Correct(false);	
		String json = gson.toJson(c);
		emisor.sendMessage(json);
	}
	
}
