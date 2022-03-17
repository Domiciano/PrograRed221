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
	public void run() {
		try {
			while(true) {	
				String msg = breader.readLine();
				if (msg == null) {
					break;
				} else {
					listener.onMessage(session, msg);
				}
			}
		} catch (IOException e) {
			System.out.println("Desconexión manejada");
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
