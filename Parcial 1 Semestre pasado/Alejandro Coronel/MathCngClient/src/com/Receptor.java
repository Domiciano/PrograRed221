package com;

import java.io.BufferedReader;
import java.io.IOException;

public class Receptor extends Thread{

	private BufferedReader breader;
	private OnMessageListener listener;
	private boolean alive = true;


	public Receptor(BufferedReader breader) {
		this.breader = breader;
	}

	@Override
	public void run() {
		try {
			while(alive) {	
				String msg = breader.readLine();
				if(msg != null) {
					if(listener == null) {
						try {
							Thread.sleep(1000);
							listener.onMessage(msg);
						} catch (InterruptedException e) {

						}
					}else {
						listener.onMessage(msg);
					}		
				}else {			
					alive = false;
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("Se desconecto");
		}

	}
	//Metodo suscripcion
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}


	public interface OnMessageListener{
		void onMessage(String msg);
	}

}
