package com;

import java.io.BufferedReader;
import java.io.IOException;

public class Receptor extends Thread{
	
	private BufferedReader bReader;
	private OnMessageListener listener;
	
	//Referencia
	private Session session;
	
	public Receptor(Session session, BufferedReader bReader) {
		this.session = session;
		this.bReader = bReader;
	}
	
	@Override
	public void run() {
		try {
			while(true) {	
				String msg = bReader.readLine();
				if(msg == null){
					break;
				}
				listener.onMessage(session, msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
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
