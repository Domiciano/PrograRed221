package comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import comm.Receptor.OnMessageLitener;

public class TCPConnection extends Thread {

	private ServerSocket server;
	private Socket socket;
	private Receptor receptor;
	private OnMessageLitener listener;
	private Emisor emisor;
	
	public TCPConnection(ServerSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		try {
			System.out.println("Esperando un cliente...");
			socket = server.accept();
			System.out.println("Cliente conectado");

			receptor = new Receptor(socket.getInputStream());
			receptor.setListener(listener);
			receptor.start();

			emisor = new Emisor(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setListenerOnMessage(OnMessageLitener listener) {
		this.listener = listener;

	}
	public Emisor getEmisor() {
		return this.emisor;
		
	}


}