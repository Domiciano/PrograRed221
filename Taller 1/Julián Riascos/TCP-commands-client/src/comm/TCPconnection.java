package comm;

import java.io.IOException;
import java.net.Socket;

import comm.Receptor.OnMessageListener;

public class TCPconnection extends Thread{

	private Socket socket;
	private String ip;
	private int puerto;
	private Receptor receptor;
	private Emisor emisor;
	private OnMessageListener listener;
	
	public TCPconnection() {}

	@Override
	public void run() {
		try {
			socket = new Socket(ip, puerto);
			
			receptor = new Receptor(socket.getInputStream());
			receptor.setListener(listener);
			receptor.start();
			
			emisor = new Emisor(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public void setListenerOfMessages(OnMessageListener listener) {
		this.listener = listener;
	}

	public Emisor getEmisor() {
		return emisor;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Socket getSocket() {
		return socket;
	}
	
}