package comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import comm.Receptor.OnMessageListener;

public class TCPConnectionServer extends Thread {

	private ServerSocket server;
	private Socket socket;
	private Emisor emisor;
	private Receptor receptor;
	private OnMessageListener listener;

	public TCPConnectionServer(ServerSocket server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Waiting...");
			socket = server.accept();
			System.out.println("Conected");
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
}
