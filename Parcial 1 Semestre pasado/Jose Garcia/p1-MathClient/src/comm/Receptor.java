package comm;

import java.io.BufferedReader;
import java.io.IOException;

public class Receptor extends Thread{
	
	private BufferedReader breader;
	private OnMessageListener listener;
	private boolean alive=true;
	
	
	public Receptor(BufferedReader breader) {
		this.breader = breader;
	}
	
	@Override
	public void run() {
		try {
			String msg="";
			while(alive && msg!=null) {	
				msg = breader.readLine();
				listener.onMessage(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(listener==null);
		}
		
	}
	//Metodo suscripcion
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
	public boolean getAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}


	public interface OnMessageListener{
		void onMessage(String msg);
	}

}
