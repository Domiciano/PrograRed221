package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import events.OnConnectionListener;

public class TCPConnection extends Thread {

	private static final String IP = "127.0.0.1";
	private static final int PORT = 6000;

	private Socket socket;
	private BufferedWriter bwriter;
	
	
	//Obbservador
	private OnConnectionListener listener;
	

	public TCPConnection() {

	}
	
	//Metodo de suscripcion
	public void setListener(OnConnectionListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			socket = new Socket(IP, PORT);
			listener.onResult("Connected");
			OutputStream os = socket.getOutputStream();
			bwriter = new BufferedWriter(new OutputStreamWriter(os));
		} catch (IOException ex) {
			ex.printStackTrace();
			listener.onResult("Connection failed");
		}
	}

	public void sendMessage(String msg) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					bwriter.write(msg + "\n");
					bwriter.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
			}
		}).start();

	}

}
