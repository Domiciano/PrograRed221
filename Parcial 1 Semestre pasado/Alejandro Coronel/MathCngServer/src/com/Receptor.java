package com;

import java.io.BufferedReader;
import java.io.IOException;

public class Receptor extends Thread{

	private BufferedReader breader;
	private OnMessageListener listener;

	//Referencia
	private Session session;
	private boolean alive = true;

	public Receptor(Session session, BufferedReader breader) {
		this.session = session;
		this.breader = breader;
	}

	@Override
	public void run() {
		try {
			while(alive) {						
					String msg = breader.readLine();
					if(msg!= null) {
				listener.onMessage(session, msg);
					}else {
						alive = false;
						return;
					}		
			}

		} catch (IOException e) {
			System.out.println("Cliente desconectado");
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
