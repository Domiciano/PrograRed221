package com;

import java.io.BufferedReader;
import java.io.IOException;

public class Receptor extends Thread {
	
	private BufferedReader breader;
	private OnMessageListener listener;
	
	
	public Receptor(BufferedReader breader) {
		this.breader = breader;
	}
	
	@Override
	public void run() {
		try {
			while(true) {	
				String msg = breader.readLine();
				if(msg == null){
					break;
				}
				listener.onMessage(msg);
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
		void onMessage(String msg);
	}

}
