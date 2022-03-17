package comm;

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
				if(msg == null) {
					break;
				}else {
					listener.onMessage(msg);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public interface OnMessageListener {
		void onMessage(String msg);
	}

	// Metodo suscripcion
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}
}
