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

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
			String mss = br.readLine();
			if(listener != null) {
				listener.onMessage(mss);
			} else {
				System.out.println("No observer");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListener(OnMessageListener listener) {
		this.listener = listener;	
	}

	public interface OnMessageListener {
		public void onMessage(String mss);
	}

}

