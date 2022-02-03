package comm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Emisor {

	private OutputStream os;
	private BufferedWriter bw;

	public Emisor(OutputStream os) {
		this.os = os;
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}

	public void sendMessage(String mss) {
		new Thread(()-> {
			try {
				bw.write(mss+"\n");
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
