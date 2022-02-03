package app;

import comm.TCPConnection;

public class Application {
	
	private TCPConnection connection;
	
	public Application() {
		connection = TCPConnection.getInstance();
		connection.setPuerto(5000);
	}
	public void init() {
		connection.start();
	}


}
