package comm;

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
					listener.onMessage(msg);
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
	
	
	public interface OnMessageListener{
		void onMessage(String msg);
	}
}
