package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Receptor extends Thread{

	private InputStream is;
	public OnMessageLitener listener;


	public Receptor(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
			String msg = br.readLine();
			if(listener != null) {
				listener.onMessage(msg);
			} else {
				System.out.println("No hay observer");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListener(OnMessageLitener listener) {
		this.listener = listener;	
	}

	public interface OnMessageLitener {
		public void onMessage(String msg);
	}

}