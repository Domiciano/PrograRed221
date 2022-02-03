package comm;

import java.io.IOException;
import java.net.Socket;
import comm.Receptor.OnMessageListener;

public class TCPConnectionClient extends Thread {

	private Socket socket;
	private Emisor emisor;
	private Receptor receptor;
	private String ip;
	private int port;
	private OnMessageListener listener;

	public TCPConnectionClient() {

	}

	@Override
	public void run() {
		try {
			socket = new Socket(ip, port);

			receptor = new Receptor(socket.getInputStream());
			receptor.setListener(listener);
			receptor.start();

			emisor = new Emisor(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListenerOnMessage(OnMessageListener listener) {
		this.listener = listener;

	}

	public Emisor getEmisor() {
		return this.emisor;

	}

	public Socket getSocket() {
		return socket;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
}
