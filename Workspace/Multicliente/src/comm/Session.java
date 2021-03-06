package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;

import main.Main;

public class Session extends Thread {

	private Socket socket;
	private String id;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private OnMessageListener listener;

	public Session(Socket socket) {
		this.socket = socket;
		id = UUID.randomUUID().toString();

	}

	@Override
	public void run() {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				String line = reader.readLine();
				System.out.println(line);
				listener.onMessage(line);
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public void sendMessage(String msg) throws IOException {
		writer.write(msg+"\n");
		writer.flush();
	}
	
	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}
	
	public interface OnMessageListener{
		void onMessage(String line);
	}

}
