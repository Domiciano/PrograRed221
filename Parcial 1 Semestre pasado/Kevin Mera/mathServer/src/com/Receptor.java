package com;

import java.io.BufferedReader;
import java.io.IOException;

public class Receptor extends Thread{

	private BufferedReader breader;
	private OnMessageListener listener;
	private boolean alive = true;

	//Referencia
	private Session session;

	public Receptor(Session session, BufferedReader breader) {
		this.session = session;
		this.breader = breader;
	}

	@Override
	public void run() {
		try {
			while(alive) {	
				String msg = breader.readLine();
				if(msg != null) {
					listener.onMessage(session, msg);
				} else {
					alive = false;
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Metodo suscripcion
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}

	public void closeReceptor() {
		alive = false;
	}

	public interface OnMessageListener{
		void onMessage(Session session, String msg);
	}
}
