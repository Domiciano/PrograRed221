package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Receptor extends Thread{

	private InputStream is;
	public OnMessageListener listener;

	public Receptor(InputStream is) {
		this.is = is;
	}

	public void run() {
		try {
			BufferedReader breader = new BufferedReader(new InputStreamReader(is));			
			String msg = breader.readLine();
			if(listener!=null) listener.onMessage(msg);
			else System.out.println("No hay observer");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}

	public interface OnMessageListener{
		public void onMessage(String msg);
	}
}
