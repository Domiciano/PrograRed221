package com;

import java.io.BufferedReader;
import java.io.IOException;

public class Receptor extends Thread{
	
	private BufferedReader breader;
	private OnMessageListener listener;
	
	//Referencia
	private Session session;
	
	public Receptor(Session session, BufferedReader breader) {
		this.session = session;
		this.breader = breader;
	}
	
	@Override
	public void run(){
		try {
			while(true) {	
				String msg = breader.readLine();
				listener.onMessage(session, msg);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("User "+session.getUser().getUsername()+" disconnected");
			TCPConnectionServer.getInstance().removeSession(session);
		}
		
	}
	//Metodo suscripcion
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
	public interface OnMessageListener{
		void onMessage(Session session, String msg);
	}

}
